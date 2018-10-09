package com.moves.movesCelebrity.configuration;

import io.dropwizard.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MovesAppConfiguration extends Configuration{

    public static final String COLLECTION_POSTS_TWITTER = "postsTwitter";
    public static final String COLLECTION_TRENDS_TWITTER = "trendsTwitter";
    public static final String COLLECTION_POSTS_HOME_TWITTER = "homePostsTwitter";
    public static final String COLLECTION_POSTS_STATUS_TWITTER = "statusPostsTwitter";
    public static final String COLLECTION_POSTS_MEDIA_STATUS_TWITTER = "statusMediaPostsTwitter";
    public static final String COLLECTION_POSTS_HOME_INSIGHTS_TWITTER = "postsInsightsTwitter";
    public static final String COLLECTION_FOLLOWERS_TWITTER = "followersTwitter";
    public static final String COLLECTION_FOLLOWING_TWITTER = "followingTwitter";

    public static final String DB_NAME = "movesCelebrity";

    public static final Map<String,String> PLATFORM_MAP ;

    public static final String ERROR_UNEXPECTED =
            "Unable to process the request at the moment. Please try after some time";

    static {

        HashMap<String, String> aMap = new HashMap<>();
        aMap.put("twitter", "SKDoss18");
        aMap.put("instagram", "408096151.9437cac.ab7a30150efe44fb81d5dc83ffa16543");
        aMap.put("twitter.trends", "Mumbai");
        aMap.put("instagram.trends", "lat=48.858844&lng=2.294351");

        PLATFORM_MAP = Collections.unmodifiableMap(aMap);

    }
}
