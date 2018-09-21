package com.moves.movesCelebrity.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moves.movesCelebrity.models.Post;
import com.moves.movesCelebrity.services.db.DBService;
import com.moves.movesCelebrity.utils.serdeser.ObjectIDGsonDeserializer;
import com.moves.movesCelebrity.utils.serdeser.ObjectIDGsonSerializer;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

public class SocialMediaPostDao {
    private static DBService mongoService = new DBService();
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .setPrettyPrinting().create();

    public static ObjectId insert(Post post, String collectionName) {
        String json = gson.toJson(post);
        Document doc = mongoService.insert(collectionName,Document.parse(json));
        ObjectId id = (ObjectId) doc.get("_id");
        return id;
    }

    public static void insert(Document document, String collectionName){
        mongoService.insert(collectionName, document);
    }

    public static void insertMany(List<Document> documents, String collectionName){
        mongoService.insert(collectionName, documents);
    }
}
