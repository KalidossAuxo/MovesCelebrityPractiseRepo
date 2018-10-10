package com.moves.movesCelebrity.social.commands.twitter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moves.movesCelebrity.social.models.TwitterFollowers;
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

public class TwitterFetchFollowers2 implements Command<ArrayList<Document>, String> {

    private Twitter twitter = TwitterFactory.getSingleton();
    private Logger logger = LoggerFactory.getLogger(TwitterPostFetchCommand.class);
    private ObjectMapper mapper = new ObjectMapper();
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .setPrettyPrinting().create();

    public TwitterFetchFollowers2() {

    }

    @Override
    public CompletableFuture<ArrayList<Document>> execute(String screenName) {
        return CompletableFuture.supplyAsync(() -> {
            ArrayList<Document> posts = null;
            try {
                posts = fetch(screenName,1,200);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return posts;
        });
    }

    public ArrayList<Document> fetch(String screenName, Integer page, Integer count) throws TwitterException, IOException {
        logger.info("Twitter fetch function called");
        Paging paging = new Paging(page, count);
        List<TwitterFollowers> post = new ArrayList<>();
        ArrayList<Document> posts;
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        IDs followerIds = twitter.getFollowersIDs(screenName,-1);
        long[] ids = followerIds.getIDs();

        for (long id : ids) {
            TwitterFollowers follower = new TwitterFollowers();
            twitter4j.User user = twitter.showUser(id);

            follower.setFollowerId(user.getId());
            follower.setFollowerName(user.getName());
            follower.setFollowerScreenName(user.getScreenName());

            post.add(follower);
        }
        posts = mapper.readValue(gson.toJson(post), new TypeReference<List<Document>>() {});

        return posts;
    }

}
