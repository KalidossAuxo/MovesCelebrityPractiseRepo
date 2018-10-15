package com.moves.movesCelebrity.services.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.UpdateResult;
import com.moves.movesCelebrity.configuration.MovesAPISystemConfiguration;
import com.moves.movesCelebrity.datastore.DBConnection;
import com.moves.movesCelebrity.datastore.MongoConnection;
import com.moves.movesCelebrity.utils.serdesr.NumberLongGsonDeserializer;
import com.moves.movesCelebrity.utils.serdesr.NumberLongGsonSerializer;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonDeserializer;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonSerializer;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class DBService implements DBServiceInterface {
    private static MongoDatabase database;
    private MongoConnection connection;
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .registerTypeAdapter(Long.class, new NumberLongGsonDeserializer())
            .registerTypeAdapter(Long.class, new NumberLongGsonSerializer())
            .setPrettyPrinting().create();

    private static Logger logger = LoggerFactory.getLogger(DBService.class);
    private static Map<DBConnection, MongoClient> mongoClients = new ConcurrentHashMap<>();

    public DBService(MongoConnection connection) {
        this.connection = connection;
        open();
    }

    private void open() {

        MongoClient mongoClient = mongoClients.get(this.connection);
        if (mongoClient == null) {
            MongoConnection connection = (MongoConnection) this.connection;
            ServerAddress serverAddress = new ServerAddress(connection.getHost(), connection.getPort());
            MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(5).build();
            mongoClient = new MongoClient(Collections.singletonList(serverAddress), options);
            mongoClients.put(this.connection, mongoClient);
        }
    }

    private MongoDatabase getMongoDb() {
        MongoClient mongoClient = mongoClients.get(this.connection);
        if (mongoClient == null) {
            open();
        }
        return mongoClient.getDatabase(this.connection.getDb());
    }

    public void close(){
        MongoClient client = mongoClients.get(this.connection);
        client.close();
    }


    public MongoCollection getCollection(String collectionName) {
        database = mongoClients.get(this.connection).getDatabase(MovesAPISystemConfiguration.getMongoDatabaseName());
        return  database.getCollection(collectionName);
    }

    public void delete (String id, String collectionName) {
        BasicDBObject queryObj = new BasicDBObject("_id", new ObjectId(id));
        getCollection(collectionName).deleteOne(queryObj);
    }

    @Override
    public Document insertDocumentWithExpirey(Document document, String collectionName) {
        if (collectionName != null) {
            MongoDatabase mongoDatabase = getMongoDb();
            MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
            collection.insertOne(document);
            return document;
        }
        return null;
    }

    public Document fetch (List<Document> queryList, String collectionName) {
        AggregateIterable<org.bson.Document> aggregateIterable = this.getCollection(collectionName).aggregate(queryList);
        return Document.parse(aggregateIterable.first().toJson());
    }

    @Override
    public Object fetchOne(String collectionName, Object searchQuery, Object sortQuery) {
        MongoCursor mongoCursor = readFromDB(collectionName, searchQuery, null, null, sortQuery, null, 1);
        if (null != mongoCursor)
            if (mongoCursor.hasNext()) {
                return (mongoCursor.next());
            }

        return null;
    }

    public Document insert (String collectionName, Document document ) {
        getCollection(collectionName).insertOne(document);
        return document;
    }

    public Document insert(String collectionName, List<Document> documents) {
        getCollection(collectionName).insertMany(documents);
        return null;
    }

    public void update (String collectionName, Document queryDocument, Document updatedDocument) {
        getCollection(collectionName).replaceOne(queryDocument, updatedDocument);
    }

    public boolean update(String collectionName, Object searchQuery, Object updateQuery) {
        try {
            MongoCollection collection = getCollection(collectionName);
            UpdateResult result = collection.updateOne(Document.parse(gson.toJson(searchQuery)),
                    new Document("$set", Document.parse(gson.toJson(updateQuery))));
            return result.getModifiedCount() > 0;
        } catch (MongoException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
    private MongoCursor readFromDB(String collectionName, Object searchQuery,
                                   List<String> selectFields, List<String> ignoreFields,
                                   Object sortQuery, Integer currentPage, int limit) {
        MongoCollection collection = getCollection(collectionName);
        MongoCursor mongoCursor;

        BasicDBObject query = new BasicDBObject();
        if (null != searchQuery) {
            query = BasicDBObject.parse(gson.toJson(searchQuery));
        }

        BasicDBObject projection = new BasicDBObject();
        if (selectFields != null) {
            selectFields.forEach(item -> {
                projection.append(item, 1);
            });
        }
        if (ignoreFields != null) {
            ignoreFields.forEach(item -> {
                projection.append(item, 0);
            });
        }

        BasicDBObject sort = new BasicDBObject();
        if (null != sortQuery) {
            sort = BasicDBObject.parse(gson.toJson(sortQuery));
        }

        if (currentPage != null) {
            mongoCursor = collection.find(query).projection(projection)
                    .sort(sort).skip((currentPage - 1) * limit).limit(limit).iterator();
        } else {
            mongoCursor = collection.find(query).projection(projection).sort(sort).iterator();
        }

        return mongoCursor;
    }


    public static <T> T parse(Object obj, Class<T> tClass) {
        if (obj == null) {
            return null;
        }
        Document doc = (Document) obj;
        try {
            return gson.fromJson(doc.toJson(), tClass);
        } catch (Exception e) {
            logger.error("Unable to parse Object to " + tClass.getCanonicalName() + ". Returning Null");
            return null;
        }
    }

    public static <T> T parse(String obj, Class<T> tClass) {
        return gson.fromJson(obj, tClass);
    }

    public static <T> String stringify(Object obj, Class<T> tClass) {
        return gson.toJson(obj, tClass);
    }

    public void createTimeIndex(String collectionName) {
        MongoDatabase mongoDatabase = getMongoDb();
        MongoCollection collection = mongoDatabase.getCollection(collectionName);
        collection.createIndex(Indexes.ascending("expireAt"),
                new IndexOptions().expireAfter(0L, TimeUnit.MILLISECONDS));
    }
}
