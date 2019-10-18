package com.example.a2019_seg2105_project.ui.clinic.register;

public class User {
    private String email;
    private String firstName;
    private boolean isLoggedin;
    private String lastName;
    private String password;
    private String role;

    public User() {

    }

    public User(String email, String firstName, boolean isLoggedin, String lastName, String password, String role) {
        this.email = email;
        this.firstName = firstName;
        this.isLoggedin = isLoggedin;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }

}
