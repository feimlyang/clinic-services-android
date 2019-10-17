package com.example.a2019_seg2105_project.data.model;

/**
 * Class LoggedInUser is a Data class that captures user information for logged in users.
 * Information is retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userEmailId;
    private String displayName;

    public LoggedInUser(String userId, String displayName) {
        this.userEmailId = userId;
        this.displayName = displayName;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
