package com.moves.movesCelebrity.application;

import com.moves.movesCelebrity.configuration.MovesConfiguration;
import com.moves.movesCelebrity.social.Invoker;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MovesApplication extends Application<MovesConfiguration> {

    private static Logger logger = LoggerFactory.getLogger(MovesApplication.class);


    public static void main(String[] args) throws Exception {
        logger.info("Main");

        //new TwitterSearchCommand().execute("Hi, GM");
        //new MovesApplication().run(args);
        // new FBPostFetchCommand().execute(null);
        //new TwitterStatusUpdateCommand().execute("Hello");
        Invoker invoker = Invoker.getInstance();
        CompletableFuture future = invoker.init().thenComposeAsync(aVoid-> {
            //return invoker.execute("twitter.status.write","Latest tweet");
            return invoker.execute("twitter.posts.media.fetch", "Hi");
        }).thenComposeAsync(docs->{
            return invoker.execute("twitter.posts.media.write" , docs);
        });
        future.get();
    }

    @Override
    public void run(MovesConfiguration movesConfiguration, Environment environment) throws Exception {
        Map<String ,String > platformMap = MovesConfiguration.PLATFORM_MAP;
        ArrayList<CompletableFuture> futureList = new ArrayList<>();
        Invoker invoker = Invoker.getInstance();
        platformMap.forEach((k,v)->{
            CompletableFuture future = invoker.init().thenComposeAsync(aVoid -> {
                return invoker.execute(k + ".posts.fetch",v);
                    }).thenComposeAsync(docs -> {
                        return invoker.execute(k+".posts.write",v);
            });
            futureList.add(future);
                });
        CompletableFuture combinedFuture = CompletableFuture.allOf(futureList
        .toArray(new CompletableFuture[futureList.size()]));
        combinedFuture.get();

    }
}
