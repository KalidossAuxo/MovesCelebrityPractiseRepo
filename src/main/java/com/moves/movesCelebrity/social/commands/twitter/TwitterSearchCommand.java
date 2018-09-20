package com.moves.movesCelebrity.social.commands.twitter;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TwitterSearchCommand implements Command<ArrayList<Document>, String> {

    private Twitter twitter = TwitterFactory.getSingleton();
    private Logger logger = LoggerFactory.getLogger(TwitterSearchCommand.class);
    private ObjectMapper mapper = new ObjectMapper();
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .setPrettyPrinting().create();


    public TwitterSearchCommand() {
    }

    @Override
    public CompletableFuture<ArrayList<Document>> execute(String searchQuery) {
        return CompletableFuture.supplyAsync(() -> {
            ArrayList<Document> posts = null;
            posts = search(searchQuery, 5);
            return posts;
        });
    }

    public ArrayList<Document> search(String searchQuery, Integer count) {
        String tweets1 = "";
        Query query = new Query();
        query.setLang("en");
        query.setQuery(searchQuery);
        query.setCount(count);
        query.setSince("2018-08-01");
        query.setUntil("2018-09-20");
        QueryResult result;
        try {
            result = twitter.search(query);
            List<Status> tweets = result.getTweets();

            logger.info("\ntweets:: " + tweets.size());
            for (Status tweet : tweets) {

                tweets1 = tweets1 + tweet.getUser().getScreenName() + " - " + tweet.getText();

                System.out.println(tweets1);
                System.out.println("Retweet Count : " + tweet.getRetweetCount());
                System.out.println("Favorite Count : " + tweet.getFavoriteCount());
                System.out.println("twitter.com/SKDoss18/status/"+tweet.getId());
                System.out.println("*****************************************************************************");

            }
            System.out.println(tweets.size());

        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
