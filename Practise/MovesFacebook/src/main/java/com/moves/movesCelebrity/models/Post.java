package com.moves.movesCelebrity.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {

    @JsonProperty("id")
    String postID="";
    @JsonProperty("text")
    String postContent="";
    @JsonProperty("")
    Integer replyCount=0;
    @JsonProperty("favoriteCount")
    Integer favCount=0;
    @JsonProperty("retweetCount")
    Integer shareCount=0;

    public Post(String postID, String postContent, Integer replyCount, Integer favCount, Integer shareCount) {
        this.postID = postID;
        this.postContent = postContent;
        this.replyCount = replyCount;
        this.favCount = favCount;
        this.shareCount = shareCount;
    }

    public Post() {
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getFavCount() {
        return favCount;
    }

    public void setFavCount(Integer favCount) {
        this.favCount = favCount;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }
}

