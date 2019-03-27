package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;
import java.util.List;

public class Spot extends Message implements Serializable {
    private String spotId;
    private byte[] video;
    private String question;
    private List<String> options;

    public Spot(String spotId, byte[] video, String question, List<String> options) {
        super(MESSAGE_TYPE.SPOT,"");
        this.spotId = spotId;
        this.video = video;
        this.question = question;
        this.options = options;
    }

    public byte[] getVideo() {
        return video;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return options;
    }

    public String getSpotId(){
        return this.spotId;
    }
}
