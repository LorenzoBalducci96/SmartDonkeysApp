package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;

public class AcquistoRequest extends Message implements Serializable {
    private String requestedCouponName;

    public AcquistoRequest(String requestedCouponName) {
        super(MESSAGE_TYPE.ACQUISTO_REQUEST, "");
        this.requestedCouponName = requestedCouponName;
    }

    public String getRequestedCouponName() {
        return requestedCouponName;
    }
}
