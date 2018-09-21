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

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class FBPostPublishCommand implements Command<Document,HashMap<String,String>> {

    private Logger logger = LoggerFactory.getLogger(FBPostFetchCommand.class);

    public FBPostPublishCommand() {
    }

    @Override
    public CompletableFuture<Document> execute(HashMap<String,String> pageDetails) {
        return CompletableFuture.supplyAsync(() -> {
            return fetch(pageDetails.get("pageId"), pageDetails.get("post"));
        });
    }

    public Document fetch(String pageId, String post){
        HttpResponse<JsonNode> httpResponse = null;
        try {
            String url = String.format(Constants.FB_PAGE_POSTS, MovesConfiguration.FB_PAGE_ID);
            httpResponse = Unirest.post(url).queryString("message",post).asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return Document.parse(httpResponse.getBody().toString());
    }
}
