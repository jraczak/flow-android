package com.justinraczak.android.flow.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by justin on 7/15/17.
 */

public class UserSession extends RealmObject {

    private static final String LOG_TAG = UserSession.class.getSimpleName();

    @Required
    public Boolean isValid;
    @Required
    public Date createdAt;
    @Required
    public Date tokenUpdatedAt;
    public User user;
    // Requisite fields for API calls
    @Required
    private String apiUid;
    @Required
    private String apiAccessToken;
    @Required
    private String apiClient;
    @Required
    private String apiTokenType;
    @Required
    private String apiExpiry;


    public UserSession() {
        // Obligatory empty constructor for Realm
    }

    public UserSession(User user, String apiUid, String apiAccessToken, String apiClient, String apiTokenType, String apiExpiry) {
        // Instantiate with the provided params
        this.user = user;
        this.apiUid = apiUid;
        this.apiAccessToken = apiAccessToken;
        this.apiClient = apiClient;
        this.apiTokenType = apiTokenType;
        this.apiExpiry = apiExpiry;

        // Auto-set these at creation time
        this.isValid = true;
        this.createdAt = new Date();
        this.tokenUpdatedAt = new Date();
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getTokenUpdatedAt() {
        return tokenUpdatedAt;
    }

    public void setTokenUpdatedAt(Date tokenUpdatedAt) {
        this.tokenUpdatedAt = tokenUpdatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getApiUid() {
        return apiUid;
    }

    public void setApiUid(String apiUid) {
        this.apiUid = apiUid;
    }

    public String getApiAccessToken() {
        return apiAccessToken;
    }

    public void setApiAccessToken(String apiAccessToken) {
        this.apiAccessToken = apiAccessToken;
    }

    public String getApiClient() {
        return apiClient;
    }

    public void setApiClient(String apiClient) {
        this.apiClient = apiClient;
    }

    public String getApiTokenType() {
        return apiTokenType;
    }

    public void setApiTokenType(String apiTokenType) {
        this.apiTokenType = apiTokenType;
    }

    public String getApiExpiry() {
        return apiExpiry;
    }

    public void setApiExpiry(String apiExpiry) {
        this.apiExpiry = apiExpiry;
    }

    // TODO: Make sure to update tokenUpdatedAt when data is refreshed

    // TODO: Helper to check if session is stale (90 days?) Auto-invalidate session when check if it is?
}
