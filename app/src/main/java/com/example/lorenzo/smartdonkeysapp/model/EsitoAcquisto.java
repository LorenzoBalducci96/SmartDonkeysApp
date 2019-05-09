package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;

public class EsitoAcquisto extends Message implements Serializable {
    private String messaggio;
    private int costo;
    private boolean acquistato;
    private String couponCode;

    public EsitoAcquisto(String messaggio, boolean acquistato, String couponCode, int costo) {
        super(MESSAGE_TYPE.ESITO_ACQUISTO, "");
        this.messaggio = messaggio;
        this.acquistato = acquistato;
        this.couponCode = couponCode;
        this.costo = costo;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public String getCouponCode(){
        return couponCode;
    }

    public boolean getEsitoAcquisto(){return acquistato; }

    public int getCosto(){
        return this.costo;
    }
}
