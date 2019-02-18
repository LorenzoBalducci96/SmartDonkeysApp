package com.example.lorenzo.smartdonkeysapp.model;

import android.widget.ImageView;

import java.io.Serializable;

public class AvailableVideoInfo implements Serializable {
    private String idVideo;
    private String nomeVideo;
    private String descrizioneVideo;
    private byte[] image;

    public AvailableVideoInfo(String idVideo, String nomeVideo, String descrizioneVideo, byte[] image) {
        this.idVideo = idVideo;
        this.nomeVideo = nomeVideo;
        this.descrizioneVideo = descrizioneVideo;
        this.image = image;
    }

    public String getIdVideo(){
        return this.idVideo;
    }

    public String getNomeVideo() {
        return nomeVideo;
    }

    public String getDescrizioneVideo() {
        return descrizioneVideo;
    }

    public byte[] getImage() {
        return image;
    }
}
