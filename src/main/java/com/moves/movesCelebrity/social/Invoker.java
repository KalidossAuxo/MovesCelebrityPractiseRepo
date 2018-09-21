package com.moves.movesCelebrity.social;

import com.moves.movesCelebrity.social.commands.twitter.*;
import com.moves.movesCelebrity.social.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class Invoker {
    private static Logger logger =  LoggerFactory.getLogger(Invoker.class);
    private Map<String , Command> commands = new HashMap<>();

    private static Invoker instance;
    private ExecutorService invokerExecPool = Executors.newFixedThreadPool(10);

    public static Invoker getInstance(){
        if(instance == null){
            instance = new Invoker();
        }
        return instance;
    }

    @Inject
    public Invoker(){
    }

    public CompletableFuture<Void> init(){
        return CompletableFuture.supplyAsync(() -> {
           commands.put("twitter.posts.fetch",new TwitterPostFetchCommand());
           commands.put("twitter.posts.write" , new TwitterPostWriteCommand());
           commands.put("twitter.trends.posts.fetch" , new TwitterTrendsFetchCommand());
           commands.put("twitter.trends.posts.write" , new TwitterTrendsWriteCommand());
           commands.put("twitter.status.posts.fetch",new TwitterStatusUpdateCommand());
           commands.put("twitter.status.posts.write" , new TwitterStatusUpdateWriteCommand());
           commands.put("twitter.homePosts.fetch",new TwitterHomePostFetchCommand());
           commands.put("twitter.homePosts.write", new TwitterHomePostWriteCommand());
           commands.put("twitter.posts.insights.fetch" , new TwitterPostInsightFetchCommand());
           commands.put("twitter.posts.insights.write" , new TwitterPostInsightWriteCommand());
           commands.put("twitter.posts.search.fetch" , new TwitterSearchCommand());

           commands.put("twitter.posts.media.fetch", new TwitterStatusMediaUpdateCommand());
            commands.put("twitter.posts.media.write", new TwitterStatusMediaUpdateCommand());
            commands.put("twitter.posts.media.write", new TwitterStatusMediaUpdateWriteCommand());

            return commands;

        }).thenAccept(stringCommandMap -> logger.info("Commands registered " + commands.toString()));
    }

    public CompletableFuture execute (String messageHandle , Object value){
        logger.info("executing ------>  " + messageHandle);
        return commands.get(messageHandle).execute(value);
    }
}

