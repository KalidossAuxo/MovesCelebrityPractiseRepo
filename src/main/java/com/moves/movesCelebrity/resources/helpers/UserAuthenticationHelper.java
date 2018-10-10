package com.moves.movesCelebrity.resources.helpers;

import com.moves.movesCelebrity.dao.UserDao;
import com.moves.movesCelebrity.models.api.APIResponse;
import com.moves.movesCelebrity.models.api.user.UserAccessTokenTwitter;

public class UserAuthenticationHelper {

    public APIResponse addTwitterAuth(UserAccessTokenTwitter auth) throws Exception {
        APIResponse response = new APIResponse();
        //if (!UserDao.findUserByEmail(request.getEmail()).isPresent()){
            UserAccessTokenTwitter token = new UserAccessTokenTwitter();
            token.setUserId(auth.getUserId());
            token.setAccessToken(auth.getAccessToken());
            token.setExpiredAt(auth.getExpiredAt());
            UserDao.insert(token, "user");//Insert to DB
            response.setData(token);
            return response;
        //}

    }
}
