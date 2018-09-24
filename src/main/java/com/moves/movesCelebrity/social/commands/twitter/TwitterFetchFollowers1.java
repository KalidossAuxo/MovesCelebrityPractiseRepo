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
import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TwitterFetchFollowers1 implements Command<ArrayList<Document>, String> {

    private Twitter twitter = TwitterFactory.getSingleton();
    private Logger logger = LoggerFactory.getLogger(TwitterTrendsFetchCommand.class);
    private ObjectMapper mapper = new ObjectMapper();
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .setPrettyPrinting().create();

    public TwitterFetchFollowers1() {
    }

    @Override
    public CompletableFuture<ArrayList<Document>> execute(String screenName) {
        return CompletableFuture.supplyAsync(()->{
            ArrayList<Document> followers = null;
            try {
                followers = fetch(screenName);
            } catch (TwitterException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return followers;

        });
    }

    public ArrayList<Document> fetch(String screenName) throws TwitterException, IOException {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        IDs followerIds = twitter.getFollowersIDs(screenName,-1);
        long[] ids = followerIds.getIDs();


        for(long id : ids){
            System.out.println("Id of the followers : " + id);
        }
        List<Long> list = new ArrayList<>();
        for (long id : ids) {
            twitter4j.User user = twitter.showUser(id);
            list.add(user.getId());
        }
        return mapper.readValue(gson.toJson(list), new TypeReference<List<Document>>() {});
    }
}
