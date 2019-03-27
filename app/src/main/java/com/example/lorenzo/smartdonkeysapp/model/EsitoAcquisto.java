package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;

public class EsitoAcquisto extends Message implements Serializable {
    private String esitoAcquisto;
    private String couponCode;

    public EsitoAcquisto(String esitoAcquisto, String couponCode) {
        super(MESSAGE_TYPE.ESITO_ACQUISTO, "");
        this.esitoAcquisto = esitoAcquisto;
        this.couponCode = couponCode;
    }

    public String getEsitoAcquisto() {
        return esitoAcquisto;
    }

    public String getCouponCode(){
        return couponCode;
    }
}
