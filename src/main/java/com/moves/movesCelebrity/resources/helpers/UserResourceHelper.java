package com.moves.movesCelebrity.resources.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moves.movesCelebrity.configuration.MovesAppConfiguration;
import com.moves.movesCelebrity.dao.UserDao;
import com.moves.movesCelebrity.models.api.APIResponse;
import com.moves.movesCelebrity.models.api.user.UserAuthDetails;
import com.moves.movesCelebrity.models.api.user.UserProfile;
import com.moves.movesCelebrity.utils.MD5Util;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonDeserializer;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonSerializer;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.moves.movesCelebrity.configuration.MovesAppConfiguration.COLLECTIONS_USER;

public class UserResourceHelper {

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .setPrettyPrinting().create();

    public APIResponse addNewUser(UserProfile request) throws Exception {
        APIResponse response = new APIResponse();
        if (!UserDao.findUserByEmail(request.getEmail()).isPresent()){
            UserProfile profile = new UserProfile();
            profile.setEmail(request.getEmail());
            profile.setPassword(MD5Util.getHashString("Moves4@"));
            profile.setFullName(request.getFullName());
            UserDao.insert(profile, "user");//Insert to DB
            response.setData(profile);
            return response;
        }
        response.setError("User already exist");
        return response;
    }

    public APIResponse signInUser(UserProfile request) throws Exception{
        APIResponse response = new APIResponse();

        //need to do Db validation here
        UserProfile profile = new UserProfile();
        profile.setEmail(request.getEmail());
        response.setData(profile);

        return response;
    }


    //For User Authentication details
    public APIResponse addUserAuthDetails(UserAuthDetails request) {
        APIResponse response = new APIResponse();
        // UserProfile userProfile = new UserProfile();
        String email=null;
        Boolean emailPresent = UserDao.findUserByEmail(email).isPresent();
        if (emailPresent==true) {

            UserAuthDetails authDetails = new UserAuthDetails();
            authDetails.setTwitterId(request.getTwitterId());
            authDetails.setTwitterAccessToken(request.getTwitterAccessToken());
            authDetails.setTwitterAccessTokenSecret(request.getTwitterAccessTokenSecret());
            authDetails.setFbId(request.getFbId());
            authDetails.setFbAccessToken(request.getFbAccessToken());
            authDetails.setInstaId(request.getInstaId());
            authDetails.setInstaAccessToken(request.getInstaAccessToken());

            Document document = Document.parse(gson.toJson(authDetails));
            UserDao.insert(document, COLLECTIONS_USER);//Insert to DB
            response.setData(document);
            return response;
        }
        response.setError(MovesAppConfiguration.EMAIL_NOT_EXIST);
        return response;
    }
}

