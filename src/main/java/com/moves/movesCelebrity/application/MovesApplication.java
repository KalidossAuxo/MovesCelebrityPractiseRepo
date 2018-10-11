package com.moves.movesCelebrity.application;

import com.moves.movesCelebrity.configuration.MovesAPISystemConfiguration;
import com.moves.movesCelebrity.resources.HealthCheckResource;
import com.moves.movesCelebrity.resources.users.TwitterPostFetchCommand;
import com.moves.movesCelebrity.resources.users.TwitterStatusUpdateCommand;
import com.moves.movesCelebrity.resources.users.UserResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MovesApplication extends Application<MovesAPISystemConfiguration> {

    private static Logger logger = LoggerFactory.getLogger(MovesApplication.class);


    public static void main(String[] args) throws Exception {
        logger.info("Main");

        //new TwitterSearchCommand().execute("Hi, GM");
        new MovesApplication().run(args);
        // new FBPostFetchCommand().execute(null);
        //new TwitterStatusUpdateCommand().execute("Hello");


//        Invoker invoker = Invoker.getInstance();
//        CompletableFuture future = invoker.init().thenComposeAsync(aVoid-> {
//            //return invoker.execute("twitter.status.write","Latest tweet");
//            return invoker.execute("twitter.posts.following.fetch", "SKDoss18");
//        }).thenComposeAsync(docs->{
//            return invoker.execute("twitter.posts.following.write" , docs);
//        });
//        future.get();
    }

    @Override
    public void run(MovesAPISystemConfiguration movesConfiguration, Environment environment) throws Exception {

        environment.jersey().register(new HealthCheckResource());
        environment.jersey().register(new UserResource());
        environment.jersey().register(new TwitterPostFetchCommand());
        environment.jersey().register(new TwitterStatusUpdateCommand());

        /*Map<String ,String > platformMap = MovesAppConfiguration.PLATFORM_MAP;
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
        combinedFuture.get();   */

    }

    @Override
    public void initialize(Bootstrap<MovesAPISystemConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle("/com/moves/movesCelebrity/assets", "/assets/"));

        bootstrap.addBundle(new SwaggerBundle<MovesAPISystemConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(MovesAPISystemConfiguration apiSystemConfiguration) {
                // this would be the preferred way to set up swagger, you can also construct the object here programmatically if you want
                return apiSystemConfiguration.swaggerBundleConfiguration;
            }
        });
    }
}
