package com.moves.movesCelebrity.social;

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
    private static Logger logger = LoggerFactory.getLogger(Invoker.class);
    private Map<String, Command> commands = new HashMap<>();
    private static Invoker instance;
    private ExecutorService invokerExecPool = Executors.newFixedThreadPool(10);


    public static Invoker getInstance() {
        if (instance == null) {
            instance = new Invoker();
        }
        return instance;
    }

    @Inject
    public Invoker() {
    }

    public CompletableFuture<Void> init() {
        return CompletableFuture.supplyAsync(() -> {
            //commands.put("twitter.posts.fetch", new TwitterPostFetchCommand());
            //commands.put("twitter.posts.write", new TwitterPostWriteCommand());
            //commands.put("twitter.trends.posts.fetch", new TwitterTrendsFetchCommand());
            //commands.put("twitter.trends.posts.write", new TwitterTrendWriteCommand());
            //commands.put("instagram.posts.fetch", new InstaPostFetchCommand());
            //commands.put("instagram.posts.write", new InstaPostWriteCommand());
            //commands.put("instagram.trends.posts.fetch", new InstaTrendsFetchCommand());
            //commands.put("instagram.trends.posts.write", new InstaPostWriteCommand());
            return commands;
        }).thenAccept(stringCommandMap -> logger.info("Commands registered " + commands.toString()));
    }

    public CompletableFuture execute(String messageHandle, Object value) {
        logger.info("executing ------>  " + messageHandle);
        return commands.get(messageHandle).execute(value);
    }
}

