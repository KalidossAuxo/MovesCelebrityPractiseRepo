package com.moves.movesCelebrity.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moves.movesCelebrity.models.api.user.UserProfile;
import com.moves.movesCelebrity.models.business.user.User;
import com.moves.movesCelebrity.services.db.DBService;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonDeserializer;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonSerializer;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {

    private static DBService mongoService = new DBService();
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .setPrettyPrinting().create();
    private static Logger logger = LoggerFactory.getLogger(UserDao.class);

    public static Object insert(UserProfile profile, String collectionName) {
        String json = gson.toJson(profile);
        Document doc = mongoService.insert(collectionName,Document.parse(json));
        return doc;
    }

    public static void insert(Document document, String collectionName){
        mongoService.insert(collectionName, document);
    }

    public static void insertMany(List<Document> documents, String collectionName){
        mongoService.insert(collectionName, documents);
    }

    public static Optional<User> findUserByEmail(String email){

        UserProfile query = new UserProfile(email);

        Object object = mongoService.fetchOne("user", query, null);
        if(null!=object){
            return Optional.of(mongoService.parse(object, User.class));
        }else {
            logger.info("findUserByEmail returned NULL for email ("+email+")");
            return Optional.empty();
        }
    }
    //Added by Kalidoss for Login Testing
    public static Optional<User> findUserByEmailAndPassword(String email,String password){

        List<UserProfile> user = new ArrayList<>();
        UserProfile query = new UserProfile(email);
        UserProfile query1 = new UserProfile(password);
        user.add(query);
        user.add(query1);

        Object object = mongoService.fetchOne("user", user, null);
        if(null!=object){
            return Optional.of(mongoService.parse(object, User.class));
        }else {
            logger.info("findUserByEmail returned NULL for email ("+email+")");
            return Optional.empty();
        }
    }
}

