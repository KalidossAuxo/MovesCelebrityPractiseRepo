package com.moves.movesCelebrity.resources.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moves.movesCelebrity.configuration.MovesAppConfiguration;
import com.moves.movesCelebrity.constants.MailerConstants;
import com.moves.movesCelebrity.dao.UserDao;
import com.moves.movesCelebrity.models.api.APIResponse;
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
    public APIResponse linkSocialMediaAccounts(UserAuthDetails request,String email) {
        APIResponse response = new APIResponse();
        if (UserDao.findUserByEmail(email).isPresent()) {
            User user = UserDao.findUserByEmail(email).get();

            UserAuthDetails authDetails = new UserAuthDetails();
            authDetails.setTwitterId(request.getTwitterId());
            authDetails.setTwitterAccessToken(request.getTwitterAccessToken());
            authDetails.setTwitterAccessTokenSecret(request.getTwitterAccessTokenSecret());
            authDetails.setFbId(request.getFbId());
            authDetails.setFbAccessToken(request.getFbAccessToken());
            authDetails.setInstaId(request.getInstaId());
            authDetails.setInstaAccessToken(request.getInstaAccessToken());

            Document document = Document.parse(gson.toJson(authDetails));
            UserDao.update(COLLECTIONS_USER, authDetails, user);//Insert to DB
            response.setData(document);
            return response;
        }
        response.setError(MovesAppConfiguration.ERROR_UNEXPECTED);
        return response;
    }
}

