package com.moves.movesCelebrity.resources.users;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moves.movesCelebrity.models.api.APIResponse;
import com.moves.movesCelebrity.social.models.TwitterPost;
import com.moves.movesCelebrity.social.types.Command;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonDeserializer;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonSerializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Path("/twitter")
@Api(value = "User Posts")
public class TwitterPostFetchCommand implements Command<ArrayList<Document>, String> {

    //private twitter4j.Twitter twitter = TwitterFactory.getSingleton();
    private Twitter twitter = TwitterFactory.getSingleton();
    private Logger logger = LoggerFactory.getLogger(TwitterPostFetchCommand.class);
    private ObjectMapper mapper = new ObjectMapper();
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .setPrettyPrinting().create();

    public TwitterPostFetchCommand() {

    }

    @Override
    public CompletableFuture<ArrayList<Document>> execute(String screenName) {
        return CompletableFuture.supplyAsync(() -> {
            ArrayList<Document> posts = null;
            try {
                posts = fetch(screenName);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return posts;
        });
    }


    @GET
    @Path("/userPosts/{screenName}")
    @ApiOperation(value = "User Posts",response = APIResponse.class)
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Document> fetch(@ApiParam(value="Screen Name",required = true) @PathParam("screenName") String screenName) throws TwitterException, IOException {
        logger.info("Twitter fetch function called");

        Integer page = 1;
        Integer count = 2000;
        Paging paging = new Paging(page, count);
        ArrayList<Document> posts;
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        List<Status> statuses = twitter.getUserTimeline(screenName, paging);
        for(Status status : statuses){
            System.out.println(status);
        }
        posts = mapper.readValue(gson.toJson(statuses), new TypeReference<List<Document>>() {});
        return posts;
    }

    public ArrayList<TwitterPost> fetch( String screenName,int page) throws TwitterException, IOException {

        page = 1;
        Integer count = 2000;
        Paging paging = new Paging(page, count);
        List<Status> statuses = null;
        ArrayList<TwitterPost> posts;
        Boolean statusEnd = twitter.getUserTimeline(screenName, paging).size() == 0 ? true : false;

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        while (!statusEnd) {
            paging.setPage(page++);
            logger.info("PAGE" + paging.getPage());
            if (statuses == null) {
                statuses = twitter.getUserTimeline(screenName, paging);
            } else {
//                statuses = ListUtils.union(statuses, twitter.getUserTimeline(screenName, paging));
            }
            logger.info("STATUS_COUNT" + twitter.getUserTimeline(screenName, paging).size());
            statusEnd = twitter.getUserTimeline(screenName, paging).size() == 0 ? true : false;
        }

        posts = mapper.readValue(gson.toJson(statuses), new TypeReference<List<TwitterPost>>() {
        });
        return posts;
    }
}
