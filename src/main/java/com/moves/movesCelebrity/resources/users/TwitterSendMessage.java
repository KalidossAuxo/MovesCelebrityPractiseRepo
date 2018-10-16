package com.moves.movesCelebrity.resources.users;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moves.movesCelebrity.models.api.APIResponse;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonDeserializer;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonSerializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.DirectMessage;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/twitter")
@Api(value = "Twitter Messaging")
public class TwitterSendMessage  {

    private static Logger logger = LoggerFactory.getLogger(TwitterSendMessage.class);
    private Twitter twitter = TwitterFactory.getSingleton();
    private ObjectMapper mapper = new ObjectMapper();
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .setPrettyPrinting().create();

    public TwitterSendMessage() {
    }

//    @Override
//    public CompletableFuture<Document> execute(String text,String screenName){
//        return CompletableFuture.supplyAsync(()->{
//            Document doc = null;
//            try{
//                doc = sendMessage(text,screenName);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            return doc;
//        });
//    }

    @POST
    @Path("/sendMessage")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Twitter Messaging", notes = "To send message from Twitter",
            response = APIResponse.class)

    public Document sendMessage(@NotNull @FormParam("screenName") String screenName ,
                                @NotNull @FormParam("text") String text) throws TwitterException, IOException {
        logger.info("Twitter send message function is called");
        Document post = null;
        //String screenName = httpHeaders.getHeaderString("UserScreenName");
        //String screenName = "SKDoss18";
        int id = 1940756599;
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        int test = 0;
        DirectMessage message = twitter.sendDirectMessage(screenName, text);
        test++;
        int count = 0;
        post = mapper.readValue(gson.toJson(message.toString()), new TypeReference<Document>() {});
        count ++;
        return post;
    }
}
