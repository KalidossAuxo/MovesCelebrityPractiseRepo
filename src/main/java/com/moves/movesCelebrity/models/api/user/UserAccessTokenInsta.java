package com.moves.movesCelebrity.models.api.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAccessTokenInsta implements Serializable {


    public static final String USER_ID = "userId";
    public static final String ACCESS_TOKEN = "accessToken";

    @SerializedName(USER_ID)
    @JsonProperty(value=USER_ID)
    private String userId;

    @SerializedName(ACCESS_TOKEN)
    @JsonProperty(value = ACCESS_TOKEN)
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
