package com.moves.movesCelebrity.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moves.movesCelebrity.models.api.user.UserProfile;
import com.moves.movesCelebrity.models.business.user.User;
import com.moves.movesCelebrity.models.query.UserIdQuery;
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

import static com.moves.movesCelebrity.configuration.MovesAppConfiguration.COLLECTIONS_USER;

public class UserDao {

    private static DBService mongoDBService;
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .setPrettyPrinting().create();
    private static Logger logger = LoggerFactory.getLogger(UserDao.class);

    public static User insert(Document profile, String collectionName) {
        Document doc = mongoDBService.insert(collectionName, profile);
        return mongoDBService.parse(doc, User.class);
    }

    public static void insertMany(List<Document> documents, String collectionName){
        mongoDBService.insert(collectionName, documents);
    }
    public static boolean update( Object searchQuery, Object updateQuery){
        return mongoDBService.update(COLLECTIONS_USER, searchQuery, updateQuery);
    }
    public static Optional<User> findUserByEmail(String email){
        UserProfile query = new UserProfile(email);
        Object object = mongoDBService.fetchOne(COLLECTIONS_USER, query, null);
        if(null!=object){
            return Optional.of(mongoDBService.parse(object, User.class));
        }else {
            logger.info("findUserByEmail returned NULL for email ("+email+")");
            return Optional.empty();
        }
    }

    //User Authentication details
    public static Optional<User> findUserByAccessToken(String accessToken){
        UserIdQuery query = new UserIdQuery();
        query.setAccessToken(accessToken);
        Object object = mongoDBService.fetchOne(COLLECTIONS_USER, query, null);
        if(null!=object){
            return Optional.of(mongoDBService.parse(object, User.class));
        }else {
            logger.info("findUserByAccessToken returned NULL for accessToken ("+accessToken+")");
            return Optional.empty();
        }
    }

//    //To remove a User from  Moves application
//    public static void delete( Document searchQuery) {
//        mongoDBService.deleteUser(COLLECTIONS_USER, searchQuery);
//    }
    //Added by Kalidoss for Login Testing
    public static Optional<User> findUserByEmailAndPassword(String email,String password){

        List<UserProfile> user = new ArrayList<>();
        UserProfile query = new UserProfile(email);
        UserProfile query1 = new UserProfile(password);
        user.add(query);
        user.add(query1);

        Object object = mongoDBService.fetchOne("user", user, null);
        if(null!=object){
            return Optional.of(mongoDBService.parse(object, User.class));
        }else {
            logger.info("findUserByEmail returned NULL for email ("+email+")");
            return Optional.empty();
        }
    }
}

