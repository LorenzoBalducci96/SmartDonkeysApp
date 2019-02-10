package com.example.lorenzo.smartdonkeysapp;

import java.io.Serializable;
import java.util.List;

public class Spot implements Serializable {
    private int spotId;
    private byte[] video;
    private String question;
    private List<String> options;

    public Spot(int spotId, byte[] video, String question, List<String> options) {
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

    public int getSpotId(){
        return this.spotId;
    }
}
