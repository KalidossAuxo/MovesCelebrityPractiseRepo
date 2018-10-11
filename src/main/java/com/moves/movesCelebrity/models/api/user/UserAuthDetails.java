package com.moves.movesCelebrity.models.api.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuthDetails implements Serializable {

    public static final String TWITTER_ACCESS_TOKEN = "twitterAccessToken";
    public static final String TWITTER_ACCESS_TOKEN_SECRET = "twitterAccessTokenSecret";
    public static final String TWITTER_USER_ID = "twitterUserId";
    public static final String FB_ACCESS_TOKEN = "fbAccessToken";
    public static final String FB_USER_ID ="fbUserId";
    public static final String INSTA_ACCESS_TOKEN = "instaAccessToken";
    public static final String INSTA_USER_ID= "instaUserId";

    @SerializedName(TWITTER_ACCESS_TOKEN)
    @JsonProperty(value = TWITTER_ACCESS_TOKEN)
    private String twitterAccessToken;
    @SerializedName(TWITTER_ACCESS_TOKEN_SECRET)
    @JsonProperty(value = TWITTER_ACCESS_TOKEN_SECRET)
    private String twitterAccessTokenSecret;
    @SerializedName(TWITTER_USER_ID)
    @JsonProperty(value = TWITTER_USER_ID)
    private String twitterId;
    @SerializedName(FB_ACCESS_TOKEN)
    @JsonProperty(value = FB_ACCESS_TOKEN)
    private String fbAccessToken;
    @SerializedName(FB_USER_ID)
    @JsonProperty(value = FB_USER_ID)
    private String fbId;
    @SerializedName(INSTA_ACCESS_TOKEN)
    @JsonProperty(value = INSTA_ACCESS_TOKEN)
    private String instaAccessToken;
    @SerializedName(INSTA_USER_ID)
    @JsonProperty(value = INSTA_USER_ID)
    private String instaId;

    public String getTwitterAccessToken() {
        return twitterAccessToken;
    }

    public void setTwitterAccessToken(String twitterAccessToken) {
        this.twitterAccessToken = twitterAccessToken;
    }

    public String getTwitterAccessTokenSecret() {
        return twitterAccessTokenSecret;
    }

    public void setTwitterAccessTokenSecret(String twitterAccessTokenSecret) {
        this.twitterAccessTokenSecret = twitterAccessTokenSecret;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public String getFbAccessToken() {
        return fbAccessToken;
    }

    public void setFbAccessToken(String fbAccessToken) {
        this.fbAccessToken = fbAccessToken;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getInstaAccessToken() {
        return instaAccessToken;
    }

    public void setInstaAccessToken(String instaAccessToken) {
        this.instaAccessToken = instaAccessToken;
    }

    public String getInstaId() {
        return instaId;
    }

    public void setInstaId(String instaId) {
        this.instaId = instaId;
    }
}

