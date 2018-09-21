/*
package com.moves.movesCelebrity.social.commands.twitter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moves.movesCelebrity.social.models.TwitterFollowers;
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

public class TwitterFetchFollowers implements Command<ArrayList<Document>,String>{

    private Twitter twitter = TwitterFactory.getSingleton();
    private Logger logger = LoggerFactory.getLogger(TwitterFetchFollowers.class);
    private ObjectMapper mapper = new ObjectMapper();
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class,new ObjectIDGsonSerializer())
            .registerTypeAdapter(ObjectId.class,new ObjectIDGsonDeserializer())
            .setPrettyPrinting().create();

    public TwitterFetchFollowers() {
    }

    @Override
    public CompletableFuture<ArrayList<Document>> execute (String screenName,Integer page){
        return CompletableFuture.supplyAsync(()->{
            ArrayList<Document> followers = null;
            try {
                followers = fetch(screenName,page);
            } catch (TwitterException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return followers;
        });
    }

    public ArrayList<Document> fetch (String screenName,int page) throws TwitterException, IOException {
        logger.info("Twitter fetch function to fetch Followers list called");
        ArrayList<Document> followers;
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        IDs followerIds = twitter.getFollowersIDs(screenName,-1);
        long[] ids = followerIds.getIDs();

        followers = mapper.readValue(gson.toJson(ids),new TypeReference<List<Document>>(){

        });
        return followers;
    }

    public ArrayList<TwitterFollowers> fetch(String screenName) throws TwitterException, IOException {
       // List<> statuses = null;
        long[] followers = null;
        IDs followerIds = null;
        ArrayList<TwitterFollowers> followersList;
        Boolean statusEnd = twitter.getFollowersIDs(screenName,-1).size() == 0 ? true : false;

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        while (!statusEnd) {
            if (followers == null) {
                 followerIds = twitter.getFollowersIDs(screenName,-1);
                long[] ids = followerIds.getIDs();
                followers = followerIds.getIDs();
            } else {
//                statuses = ListUtils.union(statuses, twitter.getUserTimeline(screenName, paging));
            }
            logger.info("STATUS_COUNT" + followerIds.getIDs().size() == 0 ? true : false);
            statusEnd = twitter.getFollowersIDs(screenName,-1).size() == 0 ? true : false;
        }

        followersList = mapper.readValue(gson.toJson(followers), new TypeReference<List<TwitterPost>>() {
        });
        return followersList;
    }
}

*/
