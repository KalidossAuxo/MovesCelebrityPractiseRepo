package com.moves.movesCelebrity.models.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserIdQuery implements Serializable{

    private static final String USER_ID = "userId";
    private static final String ACCESS_TOKEN = "accessToken";

    @JsonProperty(USER_ID)
    @SerializedName(USER_ID)
    private String userId;

    @JsonProperty(ACCESS_TOKEN)
    @SerializedName(ACCESS_TOKEN)
    private String accessToken;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
