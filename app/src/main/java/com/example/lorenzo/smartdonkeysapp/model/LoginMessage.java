package com.example.lorenzo.smartdonkeysapp.model;

import android.app.Application;

import java.io.Serializable;

public class LoginMessage extends Message implements Serializable {
    private String email;
    private String password;//tutta la comunicazione verra cifrata

    public LoginMessage(String email, String password) {
        super(MESSAGE_TYPE.LOGIN_MESSAGE,"");
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
