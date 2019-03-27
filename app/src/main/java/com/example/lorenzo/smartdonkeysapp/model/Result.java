package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;

public class Result extends Message implements Serializable {
    String result;
    int earnedCoins;
    //other useful data....

    public String getResult() {
        return result;
    }

    public int getEarnedCoins() {
        return earnedCoins;
    }

    public Result(String result, int earnedCoins) {
        super(MESSAGE_TYPE.RESULT, "");
        this.earnedCoins = earnedCoins;
        this.result = result;
    }
}
