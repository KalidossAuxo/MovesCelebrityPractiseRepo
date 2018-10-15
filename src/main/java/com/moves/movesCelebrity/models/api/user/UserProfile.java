package com.moves.movesCelebrity.models.api.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfile {

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String  SUBSCRIPTION_PLAN = "subscriptionPlan";

    enum SubscriptionPlan{BASIC, PREMIUM, AGENCY}

    @NotNull
    @SerializedName(FIRST_NAME)
    @JsonProperty(value = FIRST_NAME)//access = JsonProperty.Access.READ_ONLY,
    private String firstName;

    @NotNull
    @SerializedName(LAST_NAME)
    @JsonProperty(value = LAST_NAME)
    private String lastName;

    @NotNull
    @Email
    @JsonProperty(EMAIL)
    @SerializedName(EMAIL)
    private String email;

    @NotNull
    @JsonProperty( value = PASSWORD)
    @SerializedName(PASSWORD)
    private String password;

    @NotNull
    @JsonProperty( value = SUBSCRIPTION_PLAN)
    private SubscriptionPlan subscriptionPlan;//BASIC/PREMIUM/AGENCY


    public UserProfile() {

    }

    public UserProfile(String email){
        this.email = email;
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
