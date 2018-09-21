package com.moves.movesCelebrity.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.moves.movesCelebrity.configuration.MovesConfiguration;
import org.bson.Document;

public class FBUtils {

    public Document getExtendedPageToken(){

        String url = String.format(Constants.FB_PAGE_AUTH_EXTENSION,
                MovesConfiguration.FB_APP_ID,
                MovesConfiguration.FB_APP_SECRET,
                MovesConfiguration.FB_ACC_TOKEN);

        HttpResponse<JsonNode> httpResponse = null;
        try {
            httpResponse = Unirest.get(url).asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return Document.parse(httpResponse.getBody().toString());
    }
}
