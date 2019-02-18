package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;

public class UserData extends Message implements Serializable {
    private boolean valid;
    private String statusMessage;
    private String email;
    private String username;
    private int coins;

    public UserData(boolean valid, String statusMessage, String email, String username, int coins) {
        super(MESSAGE_TYPE.USER_DATA_MESSAGE, "");
        this.valid = valid;
        this.statusMessage = statusMessage;
        this.email = email;
        this.username = username;
        this.coins = coins;
    }

    public boolean isValid() {
        return valid;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public int getCoins() {
        return coins;
    }
}
