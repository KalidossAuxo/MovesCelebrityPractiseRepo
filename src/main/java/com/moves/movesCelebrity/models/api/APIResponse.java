package com.moves.movesCelebrity.models.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse {
    public static final Integer STATUS_SUCCESS = 0;
    public static final Integer STATUS_FAIL = 1;
    public static final Integer STATUS_NOT_FOUND = 2;

    @JsonProperty("status")
    private Integer status = STATUS_SUCCESS;
    @JsonProperty("error")
    private String error;
    @JsonProperty("data")
    private Object data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
        this.setStatus(STATUS_FAIL);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

