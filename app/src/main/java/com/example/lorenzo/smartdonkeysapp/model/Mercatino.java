package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Mercatino extends Message implements Serializable {
    private Map<Coupon, Integer> listaCoupons;

    public Mercatino(){
        super(MESSAGE_TYPE.MERCATINO, "");
        listaCoupons = new HashMap<Coupon, Integer>();
    }

    public void add(Coupon coupon, Integer quantita){
        listaCoupons.put(coupon, quantita);
    }

    public Map<Coupon, Integer> getListaCoupons(){
        return this.listaCoupons;
    }
}
