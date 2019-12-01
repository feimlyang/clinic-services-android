package com.example.a2019_seg2105_project.helpers;

public class GlobalObjectManager {

    private static volatile GlobalObjectManager instance;

    private String currentUsername;

    private GlobalObjectManager() {}

    public static GlobalObjectManager getInstance() {
        if (instance == null) {
            instance = new GlobalObjectManager();
        }
        return instance;
    }

    public final String getCurrentUsername(){
        if (currentUsername == null) throw new NullPointerException("username is null");
        return currentUsername;
    }

    public void setCurrentUsername(String userName){
        this.currentUsername = userName;
    }

}
