package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;

public class Coupon extends Message implements Serializable {

    private String nomeCoupon;
    private String descrizione;
    private int costo;
    private String link;
    private byte[] image;

    public String getDescrizione() {
        return descrizione;
    }

    public int getCosto() {
        return costo;
    }

    public Coupon(String nomeCoupon, String descrizione, int costo, String link, byte[] image){
        super(MESSAGE_TYPE.COUPON, "");
        this.nomeCoupon = nomeCoupon;
        this.link = link;
        this.image = image;
        this.descrizione = descrizione;
        this.costo = costo;

    }

    public String getNomeCoupon() {
        return nomeCoupon;
    }

    public String getLink() {
        return link;
    }

    public byte[] getImage() {
        return image;
    }
}
