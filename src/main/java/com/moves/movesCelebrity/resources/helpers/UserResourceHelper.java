package com.moves.movesCelebrity.resources.helpers;

import com.moves.movesCelebrity.dao.UserDao;
import com.moves.movesCelebrity.models.api.APIResponse;
import com.moves.movesCelebrity.models.api.user.UserProfile;
import com.moves.movesCelebrity.utils.MD5Util;

public class UserResourceHelper {


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
}

