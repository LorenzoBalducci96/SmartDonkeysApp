package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;

public class VideoRequestMessage extends Message implements Serializable {
    private String spotId;

    public VideoRequestMessage(String spotId){
        super(MESSAGE_TYPE.REQUEST_VIDEO, "");
        this.spotId = spotId;
    }

    public String getSpotId(){
        return this.spotId;
    }
}
