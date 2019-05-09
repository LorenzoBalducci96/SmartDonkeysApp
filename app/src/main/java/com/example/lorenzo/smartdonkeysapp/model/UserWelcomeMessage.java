package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class UserWelcomeMessage extends Message implements Serializable {
    private String username;
    private String profileImage;
    private String welcomeMessage;
    private Long countdownLottery;
    private int coins;
    private int tickets;

    public UserWelcomeMessage(String username, String profileImage, String welcomeMessage, int coins, int tickets){
        super(MESSAGE_TYPE.USER_WELCOME_MESSAGE, "");
        this.username = username;
        this.profileImage = profileImage;

        this.welcomeMessage = welcomeMessage;
        this.coins = coins;
        this.tickets = tickets;
    }

    public String getUsername() {
        return username;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
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

    public void setCountdownLottery(Long countdownLottery){
        this.countdownLottery = countdownLottery;
    }

    public Long getCountdownLottery(){
        return this.countdownLottery;
    }

    public boolean tickCountdown(){
        this.countdownLottery--;
        if(countdownLottery > 0)
            return true;
        else
            return false;
    }
}
