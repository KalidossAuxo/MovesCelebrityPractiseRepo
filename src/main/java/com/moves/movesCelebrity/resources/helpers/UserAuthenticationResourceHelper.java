//package com.moves.movesCelebrity.resources.helpers;
//
//import com.moves.movesCelebrity.dao.UserDao;
//import com.moves.movesCelebrity.models.api.APIResponse;
//import com.moves.movesCelebrity.models.api.user.UserAccessTokenFB;
//import com.moves.movesCelebrity.models.api.user.UserAccessTokenInsta;
//import com.moves.movesCelebrity.models.api.user.UserAccessTokenTwitter;
//import com.moves.movesCelebrity.models.api.user.UserProfile;
//
//public class UserAuthenticationResourceHelper {
//
//    public APIResponse addTwitterAuth(UserAccessTokenTwitter auth) throws Exception {
//        APIResponse response = new APIResponse();
//
//        UserProfile request = null;
//
//        //if (!UserDao.findUserByEmail(request.getEmail()).isPresent()){
//            UserAccessTokenTwitter token = new UserAccessTokenTwitter();
//            token.setUserId(auth.getUserId());
//            token.setAccessToken(auth.getAccessToken());
//            token.setAccessTokenSecret(auth.getAccessTokenSecret());
//            UserDao.insert(token, "twitterAuthDetails");//Insert to DB
//            response.setData(token);
//            return response;
////        }
////        response.setError("User already exist");
////        return response;
//    }
//
//    public APIResponse addFBAuth(UserAccessTokenFB auth) throws Exception {
//        APIResponse response = new APIResponse();
//
//        UserProfile request = null;
//
//       //if (!UserDao.findUserByEmail(request.getEmail()).isPresent()){
//            UserAccessTokenFB token = new UserAccessTokenFB();
//            token.setUserId(auth.getUserId());
//            token.setShortAccessToken(auth.getShortAccessToken());
//            token.setLongAccessToken(auth.getLongAccessToken());
//            UserDao.insert(token, "FBAuthDetails");//Insert to DB
//            response.setData(token);
//            return response;
////        }
////        response.setError("User already exist");
////        return response;
//    }
//
//    public APIResponse addInstaAuth(UserAccessTokenInsta auth) throws Exception {
//        APIResponse response = new APIResponse();
//
//        UserProfile request = null;
//
//       // if (!UserDao.findUserByEmail(request.getEmail()).isPresent()){
//            UserAccessTokenInsta token = new UserAccessTokenInsta();
//            token.setUserId(auth.getUserId());
//            token.setAccessToken(auth.getAccessToken());
//            UserDao.insert(token, "InstaAuthDetails");//Insert to DB
//            response.setData(token);
//            return response;
////        }
////        response.setError("User already exist");
////        return response;
//    }
//}
