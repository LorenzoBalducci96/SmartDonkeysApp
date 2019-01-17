package com.example.lorenzo.smartdonkeysapp;

import java.io.Serializable;

public class LoginPacket implements Serializable {
    private boolean success_login;
    private String username;

    public LoginPacket(boolean success_login, String username){
        this.success_login = success_login;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public boolean isSuccess_login() {
        return success_login;
    }
}
