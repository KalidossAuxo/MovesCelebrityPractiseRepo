package com.moves.movesCelebrity.configuration;

import io.dropwizard.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MovesConfiguration extends Configuration {

    public static final String COLLECTIONS_ACCOUNTS_LIST = "facebook_pages";
    public static final String FB_ACC_TOKEN = "EAAdur83HopYBADoYuUW5Wrj8Opv1lIOVvZBZBxKux64IXiBIpZCw7JHK4ahLIUiKtpiE6Vr0b9sMjx4RRxzk0dYsMQAWNjiRveFZBwZB1YQ23KnBQJdhXdrvvKkt1CZCPQPNyAuacsiZCPbBHZAwXugRt6Igysxx99vczwE9RMMdFtitBCQpkfMnhe4DRJdTIN8ZD";
    public static final String FB_PAGE_ID = "274808709790050";
    public static final String FB_APP_SECRET = "362308f4c8c529c4dc6b1304b9f8172b";
    public static final String FB_APP_ID = "2092026187719318";
    public static final String INSTAGRAM_ACCESS_TOKEN = "408096151.9437cac.ab7a30150efe44fb81d5dc83ffa16543";

    public static final String DB_NAME = "moves-celebrity";
    public static final Map<String, String> PLATFORM_MAP;

    static {
        HashMap<String, String> aMap = new HashMap<>();
        aMap.put("twitter", "dulquer");
        aMap.put("instagram", "408096151.9437cac.ab7a30150efe44fb81d5dc83ffa16543");
        aMap.put("twitter.trends", "Mumbai");
        aMap.put("instagram.trends", "lat=48.858844&lng=2.294351");
        PLATFORM_MAP = Collections.unmodifiableMap(aMap);
    }
}
