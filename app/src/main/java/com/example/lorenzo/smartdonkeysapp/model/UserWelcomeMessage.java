package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;

public class UserWelcomeMessage extends Message implements Serializable {
    private String username;
    private byte[] profileImage;
    private String welcome_message;
    private int coins;
    private int tickets;

    public UserWelcomeMessage(String username, byte[] profileImage, String welcome_message, int coins, int tickets){
        super(MESSAGE_TYPE.USER_WELCOME_MESSAGE, "");
        this.username = username;
        this.profileImage = profileImage;

        this.welcome_message = welcome_message;
        this.coins = coins;
        this.tickets = tickets;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public String getWelcomeMessage() {
        return welcome_message;
    }

    public int getCoins() {
        return coins;
    }

    public int getTickets() {
        return tickets;
    }

    public void updateCoins(int earnedCoins){
        this.coins += earnedCoins;
    }

    public void updateTickets(int earnedTickets){
        this.tickets += earnedTickets;
    }
}
