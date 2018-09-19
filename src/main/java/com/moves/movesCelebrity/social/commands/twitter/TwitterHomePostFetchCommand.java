package com.moves.movesCelebrity.social.commands.twitter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moves.movesCelebrity.social.models.TwitterPost;
import com.moves.movesCelebrity.social.types.Command;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonDeserializer;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonSerializer;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TwitterHomePostFetchCommand implements Command<ArrayList<Document>,String> {

    private Twitter twitter = TwitterFactory.getSingleton();
    private Logger logger = LoggerFactory.getLogger(TwitterHomePostFetchCommand.class);
    private ObjectMapper mapper = new ObjectMapper();
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .setPrettyPrinting().create();

    public TwitterHomePostFetchCommand() {
    }

    @Override
    public CompletableFuture<ArrayList<Document>> execute(String screenName) {
        return CompletableFuture.supplyAsync(() -> {
            ArrayList<Document> status = null;
            try {
                status = fetch(screenName, 1, 200);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return status;
        });
    }

    public ArrayList<Document> fetch(String screenName, Integer page, Integer count) throws TwitterException, IOException {
        logger.info("Twitter fetch function to fetch User Home posts called");
        Paging paging = new Paging();
        ArrayList<Document> status;
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        List<Status> statuses = twitter.getHomeTimeline(paging);

        status = mapper.readValue(gson.toJson(statuses), new com.fasterxml.jackson.core.type.TypeReference<List<Document>>() {
        });
        return status;
    }

    public ArrayList<TwitterPost> fetch() throws TwitterException, IOException {

        Integer page = 1;
        Integer count = 2000;
        Paging paging = new Paging(page, count);
        List<Status> statuses = null;
        ArrayList<TwitterPost> posts;
        Boolean statusEnd = twitter.getHomeTimeline(paging).size() == 0 ? true : false;

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        while (!statusEnd) {
            paging.setPage(page++);
            logger.info("PAGE" + paging.getPage());
            if (statuses == null) {
                statuses = twitter.getHomeTimeline(paging);
                for(Status status : statuses){
                    System.out.println(status);
                }
            } else {
//                statuses = ListUtils.union(statuses, twitter.getUserTimeline(screenName, paging));
            }
            logger.info("STATUS_COUNT" + twitter.getHomeTimeline(paging).size());
            statusEnd = twitter.getHomeTimeline(paging).size() == 0 ? true : false;
        }

        posts = mapper.readValue(gson.toJson(statuses), new TypeReference<List<TwitterPost>>() {
        });
        return posts;
    }
}


