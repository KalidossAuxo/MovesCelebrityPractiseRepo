package com.moves.movesCelebrity.services.db;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.moves.movesCelebrity.configuration.MovesConfiguration;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

public class DBService implements DBServiceInterface {

    private static MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));;
    private static MongoDatabase database;
    private String dbString = MovesConfiguration.DB_NAME;

    public MongoCollection getCollection(String collectionName) {
        database = mongoClient.getDatabase(dbString);
        return  database.getCollection(collectionName);
    }

    public void delete (String id, String collectionName) {
        BasicDBObject queryObj = new BasicDBObject("_id", new ObjectId(id));
        getCollection(collectionName).deleteOne(queryObj);
    }

    public Document fetch (List<Document> queryList, String collectionName) {
        AggregateIterable<Document> aggregateIterable = this.getCollection(collectionName).aggregate(queryList);
        return Document.parse(aggregateIterable.first().toJson());
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

}

