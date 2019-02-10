package com.example.lorenzo.smartdonkeysapp;

import java.io.Serializable;

public class UserWelcome implements Serializable {
    private boolean success_login;
    private String username;
    private String welcome_or_error_message;

    public UserWelcome(boolean success_login, String username, String welcome_or_error_message){
        this.success_login = success_login;
        this.username = username;
        this.welcome_or_error_message = welcome_or_error_message;

    }

    public String getUsername() {
        return username;
    }

    public boolean isSuccess_login() {
        return success_login;
    }

    public String getWelcome_or_error_message() {
        return welcome_or_error_message;
    }
}
