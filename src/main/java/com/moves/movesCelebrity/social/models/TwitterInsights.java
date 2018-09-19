package com.moves.movesCelebrity.social.models;

public class TwitterInsights {
    String postId;
    String postContent;
    Integer favoriteCount;
    Integer shareCount;
    Integer retweetCount;

    public TwitterInsights(String postId, String postContent, Integer favoriteCount, Integer shareCount, Integer retweetCount) {
        this.postId = postId;
        this.postContent = postContent;
        this.favoriteCount = favoriteCount;
        this.shareCount = shareCount;
        this.retweetCount = retweetCount;
    }

    public TwitterInsights() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Integer getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(Integer retweetCount) {
        this.retweetCount = retweetCount;
    }
}
