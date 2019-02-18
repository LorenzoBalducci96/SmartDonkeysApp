package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;

public class CreateAccountMessage extends Message implements Serializable {
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

    public CreateAccountMessage(String email, String username, String password, String preferenze) {
        super(MESSAGE_TYPE.CREATE_ACCOUNT_MESSAGE, "");
        this.email = email;
        this.username = username;
        this.password = password;
        Preferenze = preferenze;
    }
}
