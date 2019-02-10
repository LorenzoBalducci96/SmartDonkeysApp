package com.example.lorenzo.smartdonkeysapp;

import java.io.Serializable;

public class CreateAccountPacket implements Serializable {
    private String email;
    private String username;
    private String password;
    private String Preferenze;

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPreferenze() {
        return Preferenze;
    }

    public CreateAccountPacket(String email, String username, String password, String preferenze) {

        this.email = email;
        this.username = username;
        this.password = password;
        Preferenze = preferenze;
    }
}
