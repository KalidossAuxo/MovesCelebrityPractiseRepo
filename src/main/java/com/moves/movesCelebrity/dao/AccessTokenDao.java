package com.moves.movesCelebrity.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moves.movesCelebrity.datastore.BaseDao;
import com.moves.movesCelebrity.models.api.user.UserAccessToken;
import com.moves.movesCelebrity.models.query.UserIdQuery;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonDeserializer;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonSerializer;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.moves.movesCelebrity.configuration.MovesAppConfiguration.COLLECTIONS_ACCESS_TOKEN;
import static com.moves.movesCelebrity.configuration.MovesAppConfiguration.COLLECTIONS_USER;

public class AccessTokenDao extends BaseDao {

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .setPrettyPrinting().create();
    private static Logger logger = LoggerFactory.getLogger(AccessTokenDao.class);


    public static Object insert(UserAccessToken accessToken, String collectionName) {
        String json = gson.toJson(accessToken);
        Document doc = mongoDBService.insert(collectionName, Document.parse(json));
        return doc;
    }

    public static Object insertAccessToken(UserAccessToken token, String collectionName){
        Document document = new Document();
        document.put("expireAt", token.getExpireAt());
        document.put("userId", token.getUserId());
        return mongoDBService.insertDocumentWithExpirey(document, collectionName);
    }


    public static Optional<UserAccessToken> findUserById(String id) {

        UserIdQuery query = new UserIdQuery();
        query.setUserId(id);
        Object object = mongoDBService.fetchOne(COLLECTIONS_ACCESS_TOKEN, query, null);
        if (null != object) {
            return Optional.of(mongoDBService.parse(object, UserAccessToken.class));
        } else {
            return Optional.empty();
        }
    }

    //Added for fetching User by Access Token

    public static Optional<UserAccessToken> findUserByAccessToken(String accessToken) {

        UserIdQuery query = new UserIdQuery();
        query.setAccessToken(accessToken);
        Object object = mongoDBService.fetchOne(COLLECTIONS_USER, query, null);
        if (null != object) {
            return Optional.of(mongoDBService.parse(object, UserAccessToken.class));
        } else {
            return Optional.empty();
        }
    }
}
