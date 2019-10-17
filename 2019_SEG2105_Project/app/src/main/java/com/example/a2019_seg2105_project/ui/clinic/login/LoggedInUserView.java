package com.example.a2019_seg2105_project.ui.clinic.login;

/**
 * LoggedInUserView is a Class that store authenticated user details.
 * Facilitates exposure of data to UI..
 */
 class LoggedInUserView {
    private String displayName;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }
}
