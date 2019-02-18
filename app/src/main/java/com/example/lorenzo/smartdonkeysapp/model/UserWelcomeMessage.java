package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;

public class UserWelcomeMessage extends Message implements Serializable {
    private boolean success_login;
    private String username;
    private String welcome_message;

    public UserWelcomeMessage(boolean success_login, String username, String welcome_message){
        super(MESSAGE_TYPE.USER_WELCOME_MESSAGE, "");
        this.success_login = success_login;
        this.username = username;
        this.welcome_message = welcome_message;
    }

    public String getUsername() {
        return username;
    }

    public boolean isSuccess_login() {
        return success_login;
    }

    public String getWelcomeMessage() {
        return welcome_message;
    }
}
