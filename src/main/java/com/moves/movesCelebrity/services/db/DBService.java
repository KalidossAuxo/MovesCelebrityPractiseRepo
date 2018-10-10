package com.moves.movesCelebrity.services.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.moves.movesCelebrity.configuration.MovesAppConfiguration;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonDeserializer;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonSerializer;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DBService implements DBServiceInterface {

    private static MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));;
    private static MongoDatabase database;
    private String dbString = MovesAppConfiguration.DB_NAME;
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
//            .registerTypeAdapter(Long.class, new NumberLongGsonDeserializer())
//            .registerTypeAdapter(Long.class, new NumberLongGsonSerializer())
            .setPrettyPrinting().create();

    private static Logger logger = LoggerFactory.getLogger(DBService.class);

    public MongoCollection getCollection(String collectionName) {
        database = mongoClient.getDatabase(dbString);
        return  database.getCollection(collectionName);
    }

    public void delete (String id, String collectionName) {
        BasicDBObject queryObj = new BasicDBObject("_id", new ObjectId(id));
        getCollection(collectionName).deleteOne(queryObj);
    }

    public Document fetch (List<Document> queryList, String collectionName) {
        AggregateIterable<org.bson.Document> aggregateIterable = this.getCollection(collectionName).aggregate(queryList);
        return Document.parse(aggregateIterable.first().toJson());
    }

    @Override
    public Object fetchOne(String collectionName, Object searchQuery, Object sortQuery) {
        MongoCursor mongoCursor = readFromDB(collectionName, searchQuery, null, null, sortQuery, null, 0);
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
}
