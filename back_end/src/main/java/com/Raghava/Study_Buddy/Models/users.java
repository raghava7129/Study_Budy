package com.Raghava.Study_Buddy.Models;

public class users {
    private String username;
    private String password;
    private String fullName;
    private String user_email;

    public users() {
    }

    public users(String username, String password, String fullName, String user_email) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.user_email = user_email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    @Override
    public String toString() {
        return "users{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", user_email='" + user_email + '\'' +
                '}';
    }
}
