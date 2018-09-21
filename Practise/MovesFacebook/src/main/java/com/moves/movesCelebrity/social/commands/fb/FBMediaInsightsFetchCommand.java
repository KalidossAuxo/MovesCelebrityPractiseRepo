package com.moves.movesCelebrity.social.commands.fb;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.moves.movesCelebrity.social.types.Command;
import com.moves.movesCelebrity.utils.Constants;
import org.bson.Document;

import java.util.concurrent.CompletableFuture;

public class FBMediaInsightsFetchCommand implements Command<Document, String> {


    public FBMediaInsightsFetchCommand() {

    }
    @Override
    public CompletableFuture<Document> execute(String arg) {
        return CompletableFuture.supplyAsync(() -> {
            Document insights = null;
            try {
                insights = getInsights(arg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return insights;
        });
    }

    private Document getInsights(String pageId) {

        String url = String.format(Constants.FACEBOOK_MEDIA_INSIGHTS_URL, pageId);
        String response = null;
        Document doc = null;
        try {
            response = Unirest.get(url).asJson().getBody().toString();
            if (response != null && !response.contains("error_code")) {
                doc = Document.parse( response.toString() );
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return doc;
    }
}

