package com.moves.movesCelebrity.models.api.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserProfile {

    public static final String FULL_NAME = "fullName";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String  USER_TYPE = "userType";

    @NotNull
    @SerializedName(FULL_NAME)
    @JsonProperty(value = FULL_NAME)//access = JsonProperty.Access.READ_ONLY,
    private String fullName;

    @NotNull
    @Email
    @JsonProperty(EMAIL)
    @SerializedName(EMAIL)
    private String email;

    @JsonProperty( value = PASSWORD)//access = JsonProperty.Access.WRITE_ONLY,
    @SerializedName(PASSWORD)
    private String password;

    @JsonProperty( value = USER_TYPE)
    private String userType = "FREE";

    public UserProfile() {

    }

    public UserProfile(String email){
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
