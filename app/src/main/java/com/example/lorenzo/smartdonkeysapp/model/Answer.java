package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;

public class Answer extends Message implements Serializable {
    private String answer;
    private String spotId;

    public Answer(String answer, String spotId) {
        super(MESSAGE_TYPE.ANSWER, "");
        this.answer = answer;
        this.spotId = spotId;
    }

    public String getAnswer() {
        return answer;
    }

    public String getSpotId() {
        return spotId;
    }
}
