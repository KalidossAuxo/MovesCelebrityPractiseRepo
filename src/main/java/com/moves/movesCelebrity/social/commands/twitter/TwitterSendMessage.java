package com.moves.movesCelebrity.social.commands.twitter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moves.movesCelebrity.social.types.Command;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonDeserializer;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonSerializer;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.DirectMessage;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class TwitterSendMessage implements Command<Document,String> {

    private static Logger logger = LoggerFactory.getLogger(TwitterSendMessage.class);
    private Twitter twitter = TwitterFactory.getSingleton();
    private ObjectMapper mapper = new ObjectMapper();
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .setPrettyPrinting().create();

    public TwitterSendMessage() {
    }

    @Override
    public CompletableFuture<Document> execute(String text){
        return CompletableFuture.supplyAsync(()->{
           Document doc = null;
           try{
              doc = sendMessage(text);
           }catch (Exception e){
               e.printStackTrace();
           }
           return doc;
        });
    }


    public Document sendMessage(String text) throws TwitterException, IOException {
        logger.info("Twitter send message function is called");
        String screenName = "SKDoss18";
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        DirectMessage message = twitter.sendDirectMessage(screenName, text);
        return mapper.readValue(gson.toJson(message),new TypeReference<Document>(){});
    }
}
