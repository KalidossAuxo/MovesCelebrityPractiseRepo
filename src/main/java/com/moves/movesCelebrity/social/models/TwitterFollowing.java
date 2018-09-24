package com.moves.movesCelebrity.social.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TwitterFollowing {
    @JsonProperty("id")
    Long friendId ;
    @JsonProperty("name")
    String friendName;
    @JsonProperty("screenName")
    String friendScreenName;

    public TwitterFollowing(Long friendId, String friendName, String friendScreenName) {
        this.friendId = friendId;
        this.friendName = friendName;
        this.friendScreenName = friendScreenName;
    }

    public TwitterFollowing() {
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendScreenName() {
        return friendScreenName;
    }

    public void setFriendScreenName(String friendScreenName) {
        this.friendScreenName = friendScreenName;
    }
}
