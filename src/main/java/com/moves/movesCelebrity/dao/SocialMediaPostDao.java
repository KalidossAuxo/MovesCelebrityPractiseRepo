package com.moves.movesCelebrity.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moves.movesCelebrity.datastore.BaseDao;
import com.moves.movesCelebrity.models.Post;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonDeserializer;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonSerializer;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

public class SocialMediaPostDao extends BaseDao{

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .setPrettyPrinting().create();

    public static ObjectId insert(Post post, String collectionName) {
        String json = gson.toJson(post);
        Document doc = mongoDBService.insert(collectionName,Document.parse(json));
        ObjectId id = (ObjectId) doc.get("_id");
        return id;
    }

    public static void insert(Document document, String collectionName){
        mongoDBService.insert(collectionName, document);
    }

    public static void insertMany(List<Document> documents, String collectionName){
        mongoDBService.insert(collectionName, documents);
    }
}
