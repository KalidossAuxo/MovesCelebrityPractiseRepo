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
import twitter4j.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class TwitterStatusMediaUpdateCommand implements Command<Document, String> {

    private Twitter twitter = TwitterFactory.getSingleton();
    private Logger logger = LoggerFactory.getLogger(TwitterStatusUpdateCommand.class);
    private ObjectMapper mapper = new ObjectMapper();
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .setPrettyPrinting().create();
    File file = new File("C:\\Users\\Auxo User\\Desktop\\jokerserious.jpg");

    public TwitterStatusMediaUpdateCommand() {
    }

    @Override
    public CompletableFuture<Document> execute(String post) {
        return CompletableFuture.supplyAsync(() -> {
            Document status = null;
            //Status status = twitter.updateStatus(post);
            try {
                status = fetch(post);
            } catch (IOException | TwitterException e) {
                e.printStackTrace();
            }
            return status;
        });
    }

    public Document fetch(String post) throws TwitterException, IOException {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        String message = post;
        StatusUpdate status = new StatusUpdate(message);
        status.setMedia(file);
        Status status1 = twitter.updateStatus(status);
        return mapper.readValue(gson.toJson(status1), new TypeReference<Document>() {});
    }
}
