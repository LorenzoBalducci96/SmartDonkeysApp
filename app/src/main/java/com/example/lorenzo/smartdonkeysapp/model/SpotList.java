package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;
import java.util.List;

public class SpotList extends Message implements Serializable {
    List<Spot> spotList;


    public SpotList(List<Spot> spotList) {
        super(MESSAGE_TYPE.VIDEO_LIST_MESSAGE, "");
        this.spotList = spotList;
    }

    public List<Spot> getSpotList() {
        return spotList;
    }
}
