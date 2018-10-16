package com.moves.movesCelebrity.resources.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moves.movesCelebrity.configuration.MovesAppConfiguration;
import com.moves.movesCelebrity.constants.MailerConstants;
import com.moves.movesCelebrity.dao.AccessTokenDao;
import com.moves.movesCelebrity.dao.UserDao;
import com.moves.movesCelebrity.models.api.APIResponse;
import com.moves.movesCelebrity.models.api.user.Account;
import com.moves.movesCelebrity.models.api.user.UserAccessToken;
import com.moves.movesCelebrity.models.api.user.UserAuthDetails;
import com.moves.movesCelebrity.models.api.user.UserProfile;
import com.moves.movesCelebrity.models.business.email.MailMessage;
import com.moves.movesCelebrity.models.business.user.User;
import com.moves.movesCelebrity.utils.MD5Util;
import com.moves.movesCelebrity.utils.email.GMailSender;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonDeserializer;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonSerializer;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.moves.movesCelebrity.configuration.MovesAppConfiguration.COLLECTIONS_USER;

public class UserResourceHelper {

    private enum Occassion {SIGN_UP, FORGOT_PWD};

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .setPrettyPrinting().create();

    public APIResponse addNewUser(UserProfile request) throws Exception {
        APIResponse response = new APIResponse();

        if (!UserDao.findUserByEmail(request.getEmail()).isPresent()) {
            request.setPassword(MD5Util.getHashString(request.getPassword()));

            int otp = (int) (Math.random() * 9000) + 1000;
            Document document = Document.parse(gson.toJson(request));
            document.append("isEmailVerified", false);
            document.append("otp", otp);

            User user = UserDao.insert(document, COLLECTIONS_USER);
            response.setData(user);
            sendEmail(Occassion.SIGN_UP, request, otp);
            return response;
        }
        response.setError(MovesAppConfiguration.EMAIL_EXIST);
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


    private void sendEmail(Occassion occasion, UserProfile userProfile, int otp) {
        MailMessage mailMessage = new MailMessage();
        mailMessage.setRecipientEmail(userProfile.getEmail());

        switch (occasion) {
            case SIGN_UP:
                mailMessage.setBody(String.format(MailerConstants.SIGNUP_BODY, userProfile.getLastName(), userProfile.getFirstName(), otp));
                mailMessage.setSubject(MailerConstants.SIGNUP_SUBJECT);
                break;
            case FORGOT_PWD:
                break;
        }
        new GMailSender().sendEmail(mailMessage);
    }

    //For User Authentication details
    public APIResponse linkSocialMediaAccounts(UserAuthDetails request, String accessToken) {
        APIResponse response = new APIResponse();

        System.out.println("The access token is :"+ accessToken);
        System.out.println("The twitter id is : "+request.getTwitterId());
        if (UserDao.findUserByAccessToken(accessToken).isPresent()) {
            Boolean access = UserDao.findUserByAccessToken(accessToken).isPresent();
            request.setTwitterId(request.getTwitterId());
            User user = UserDao.findUserByAccessToken(accessToken).get();

            request.setTwitterAccessToken(request.getTwitterAccessToken());
            request.setTwitterAccessTokenSecret(request.getTwitterAccessTokenSecret());
            request.setFbId(request.getFbId());
            request.setFbAccessToken(request.getFbAccessToken());
            request.setInstaId(request.getInstaId());
            request.setInstaAccessToken(request.getInstaAccessToken());

            Document document = Document.parse(gson.toJson(request));
            int count = 0;
            UserDao.update(user,request);//Insert to DB
            count++;
            if(count>0){
                System.out.println("Data inserted successfully");
            }
            response.setData(document);
            return response;
        }
        response.setError(MovesAppConfiguration.ERROR_UNEXPECTED);
        return response;
    }

    //For Social media Account sign out

    public APIResponse removeSocialMediaAccounts(Account account , String accessToken) {
        APIResponse response = new APIResponse();

        System.out.println("The access token is :"+ accessToken);
        if (AccessTokenDao.findUserByAccessToken(accessToken).isPresent()) {
            UserAccessToken user = AccessTokenDao.findUserByAccessToken(accessToken).get();

            System.out.println("User is " +user);

            UserAuthDetails request = new UserAuthDetails();
            if(account.getAccount() == "twitter"){
                request.setTwitterId(null);
                request.setTwitterAccessToken(null);
                request.setTwitterAccessTokenSecret(null);
            }
            if(account.getAccount() == "fb"){
                request.setFbId(null);
                request.setFbAccessToken(null);
            }
            if (account.getAccount() == "instagram"){
                request.setInstaId(null);
                request.setInstaAccessToken(null);
            }
            Document document = Document.parse(gson.toJson(request));
            int count = 0;
            UserDao.update(user,request);//Insert to DB
            count++;
            if(count>0){
                System.out.println("Data removed successfully");
            }
            response.setData(document);
            return response;
        }
        response.setError(MovesAppConfiguration.ERROR_UNEXPECTED);
        return response;
    }

//    //For removing a User from Moves Application
//
//    public APIResponse removeMovesUser(String accessToken) {
//        APIResponse response = new APIResponse();
//
//        System.out.println("The access token is :"+ accessToken);
//        if (AccessTokenDao.findUserByAccessToken(accessToken).isPresent()) {
//            UserAccessToken user = AccessTokenDao.findUserByAccessToken(accessToken).get();
//
//            Document document = Document.parse(gson.toJson(user));
//            int count = 0;
//            UserDao.delete(document);//Insert to DB
//            count++;
//            if(count>0){
//                System.out.println("Data removed successfully");
//            }
//            response.setData(document);
//            return response;
//        }
//        response.setError(MovesAppConfiguration.ERROR_UNEXPECTED);
//        return response;
//    }
}

