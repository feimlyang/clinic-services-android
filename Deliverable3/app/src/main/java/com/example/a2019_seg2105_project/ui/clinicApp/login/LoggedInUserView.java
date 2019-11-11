package com.example.a2019_seg2105_project.ui.clinicApp.login;

import java.io.Serializable;

/**
 * LoggedInUserView is a Class that store authenticated user details.
 * Facilitates exposure of data to UI..
 */
public class LoggedInUserView implements Serializable {
    private String displayName;
    private String role;

    public LoggedInUserView(String displayName, String role) {
        this.displayName = displayName;
        this.role = role;
    }

    public String getDisplayName() {
        return displayName;
    }
    public String getRole() {
        return role;
    }
}
