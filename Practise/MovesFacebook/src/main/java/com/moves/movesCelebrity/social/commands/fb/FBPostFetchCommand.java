package com.moves.movesCelebrity.social.commands.fb;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.moves.movesCelebrity.configuration.MovesConfiguration;
import com.moves.movesCelebrity.social.types.Command;
import com.moves.movesCelebrity.utils.Constants;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FBPostFetchCommand implements Command<Document,String> {

    private Logger logger = LoggerFactory.getLogger(FBPostFetchCommand.class);
    private ExecutorService fbFetchExecPool = Executors.newFixedThreadPool(10);

    public FBPostFetchCommand() {
    }

    @Override
    public CompletableFuture<Document> execute(String pageId) {
        return CompletableFuture.supplyAsync(() -> {
            return fetch(pageId);
        });
    }

    public Document fetch(String pageId){
        HttpResponse<JsonNode> httpResponse = null;
        try {
            String url = String.format(Constants.FB_PAGE_POSTS, MovesConfiguration.FB_PAGE_ID);
            httpResponse = Unirest.get(url).asJson();
            logger.info(String.format("\n\nPage Feeds %s", httpResponse.getBody().toString()));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return Document.parse(httpResponse.getBody().toString());
    }
}

