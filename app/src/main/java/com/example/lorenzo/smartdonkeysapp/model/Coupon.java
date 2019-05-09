package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;

public class Coupon extends Message implements Serializable {

    private String tipologia;
    private String nomeCoupon;
    private String premio;
    private String descrizione;
    private int costo;
    private String immagine;

    public Coupon(String tipologia, String nomeCoupon, String premio,
                  String descrizione, int costo, String immagine) {
        super(MESSAGE_TYPE.MERCATINO, "");
        this.tipologia = tipologia;
        this.nomeCoupon = nomeCoupon;
        this.premio = premio;
        this.descrizione = descrizione;
        this.costo = costo;
        this.immagine = immagine;
    }

    public String getTipologia() {
        return tipologia;
    }

    public String getNomeCoupon() {
        return nomeCoupon;
    }

    public String getPremio() {
        return premio;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getCosto() {
        return costo;
    }

    public String getImmagine() {
        return immagine;
    }
}
