package com.moves.movesCelebrity.social.models;

public class TwitterFollowers {
    Long followerId;
    String followerName;
    String followerScreenName;

    public TwitterFollowers(Long followerId, String followerName, String followerScreenName) {
        this.followerId = followerId;
        this.followerName = followerName;
        this.followerScreenName = followerScreenName;
    }

    public TwitterFollowers() {
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public String getFollowerName() {
        return followerName;
    }

    public void setFollowerName(String followerName) {
        this.followerName = followerName;
    }

    public String getFollowerScreenName() {
        return followerScreenName;
    }

    public void setFollowerScreenName(String followerScreenName) {
        this.followerScreenName = followerScreenName;
    }
}
