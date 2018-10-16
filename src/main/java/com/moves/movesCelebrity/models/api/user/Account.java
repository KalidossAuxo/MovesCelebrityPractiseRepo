package com.moves.movesCelebrity.models.api.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account implements Serializable {

    public static final String ACCOUNT = "Account";

    @SerializedName(ACCOUNT)
    @JsonProperty(value = ACCOUNT)
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
