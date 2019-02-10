package com.example.lorenzo.smartdonkeysapp;

import android.app.Application;

import java.io.Serializable;

public class LoginPacket implements Serializable {
    private String email;
    private String password;//tutta la comunicazione verra cifrata

    public LoginPacket(String email, String password) {
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
