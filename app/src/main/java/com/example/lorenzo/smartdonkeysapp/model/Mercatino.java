package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mercatino extends Message implements Serializable {
    private List<Coupon> listaCoupons;

    public Mercatino(List<Coupon> couponList){
        super(MESSAGE_TYPE.MERCATINO, "");
        listaCoupons = couponList;
    }

    public List<Coupon> getListaCoupons(){
        return this.listaCoupons;
    }
}
