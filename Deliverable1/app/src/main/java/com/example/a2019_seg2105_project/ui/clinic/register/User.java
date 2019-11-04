package com.example.a2019_seg2105_project.ui.clinic.register;

public class User {
    String id;
    public String email;
    public String firstName;
    public boolean isLoggedin;
    public String lastName;
    public String password;
    public String role;

    public User() {

    }

    public User(String id, String email, String firstName, boolean isLoggedin, String lastName, String password, String role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.isLoggedin = isLoggedin;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
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
