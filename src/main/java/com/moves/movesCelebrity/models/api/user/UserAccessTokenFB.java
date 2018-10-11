//package com.moves.movesCelebrity.models.api.user;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.google.gson.annotations.SerializedName;
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class UserAccessTokenFB {
//    public static final String USER_ID = "userId";
//    public static final String SHORT_ACCESS_TOKEN = "shortAccessToken";
//    public static final String LONG_ACCESS_TOKEN = "longAccessToken";
//
//    @SerializedName(USER_ID)
//    @JsonProperty(value=USER_ID)
//    private String userId;
//
//    @SerializedName(SHORT_ACCESS_TOKEN)
//    @JsonProperty(value = SHORT_ACCESS_TOKEN)
//    private String shortAccessToken;
//
//    @SerializedName(LONG_ACCESS_TOKEN)
//    @JsonProperty(value = LONG_ACCESS_TOKEN)
//    private String longAccessToken;
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getShortAccessToken() {
//        return shortAccessToken;
//    }
//
//    public void setShortAccessToken(String shortAccessToken) {
//        this.shortAccessToken = shortAccessToken;
//    }
//
//    public String getLongAccessToken() {
//        return longAccessToken;
//    }
//
//    public void setLongAccessToken(String longAccessToken) {
//        this.longAccessToken = longAccessToken;
//    }
//}
