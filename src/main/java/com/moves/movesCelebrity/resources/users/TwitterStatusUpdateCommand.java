package com.moves.movesCelebrity.resources.users;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moves.movesCelebrity.models.api.APIResponse;
import com.moves.movesCelebrity.social.types.Command;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonDeserializer;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonSerializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Path("/twitter")
@Api("Twitter Status Update")
public class TwitterStatusUpdateCommand implements Command<Document, String>{

    private Twitter twitter = TwitterFactory.getSingleton();
    private Logger logger = LoggerFactory.getLogger(TwitterStatusUpdateCommand.class);
    private ObjectMapper mapper = new ObjectMapper();
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .setPrettyPrinting().create();

    public TwitterStatusUpdateCommand() {
    }

    @Override
    public CompletableFuture<Document> execute(String post) {
        return CompletableFuture.supplyAsync(() -> {
            Document status = null;
            //Status status = twitter.updateStatus(post);
            try {
                status = updateStatus(post);
            } catch (IOException | TwitterException e) {
                e.printStackTrace();
            }

            return status;
        });
    }

    @POST
    @Path("/status")
    @ApiOperation(value = "Twitter status",response = APIResponse.class)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Document updateStatus(@NotNull @FormParam("post") String post) throws TwitterException, IOException {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        Status status = twitter.updateStatus(post);
        return mapper.readValue(gson.toJson(status), new TypeReference<Document>() {});
    }
}
