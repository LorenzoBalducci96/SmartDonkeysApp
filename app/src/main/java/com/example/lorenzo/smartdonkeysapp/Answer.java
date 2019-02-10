package com.example.lorenzo.smartdonkeysapp;

import java.io.Serializable;

public class Answer implements Serializable {
    private String answer;
    private int spotId;

    public Answer(String answer, int spotId) {
        this.answer = answer;
        this.spotId = spotId;
    }

    public String getAnswer() {
        return answer;
    }

    public int getSpotId() {
        return spotId;
    }
}
