package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VideoListMessage extends Message implements Serializable {

    List<AvailableVideoInfo> listaVideoDisponibili;

    public VideoListMessage(){
        super(MESSAGE_TYPE.VIDEO_LIST_MESSAGE, "");
        listaVideoDisponibili = new ArrayList<>();
    }

    public void addVideoInfo(AvailableVideoInfo videoInfo){
        listaVideoDisponibili.add(videoInfo);
    }

    public List<AvailableVideoInfo> getListaVideoDisponibili(){
        return listaVideoDisponibili;
    }
}
