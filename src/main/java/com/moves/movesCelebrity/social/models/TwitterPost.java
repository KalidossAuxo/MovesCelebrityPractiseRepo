package com.moves.movesCelebrity.social.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
@ApiModel
public class TwitterPost {
    @ApiModelProperty(value="posts",required = true)
    private HashMap<String, Object> posts;

    public HashMap<String, Object> getPosts() {
        return posts;
    }

    public void setPosts(HashMap<String, Object> posts) {
        this.posts = posts;
    }
}
