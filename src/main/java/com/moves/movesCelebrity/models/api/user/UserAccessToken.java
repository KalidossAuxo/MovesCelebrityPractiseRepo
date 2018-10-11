package com.moves.movesCelebrity.models.api.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDJacksonSerializer;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAccessToken implements Serializable{

    public static final String USER_ID = "userId";
    public static final String ACCESS_TOKEN = "accessToken";

    @SerializedName(USER_ID)
    @JsonProperty(value = USER_ID)//access = JsonProperty.Access.READ_ONLY,
    private String userId;

    @JsonProperty(ACCESS_TOKEN)
    @SerializedName("_id")
    @JsonSerialize(using = ObjectIDJacksonSerializer.class)
    ObjectId id;

    private Date expireAt;

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }

    public ObjectId getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
