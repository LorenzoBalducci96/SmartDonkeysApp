package com.example.lorenzo.smartdonkeysapp.model.poker;

import com.example.lorenzo.smartdonkeysapp.model.MESSAGE_TYPE;
import com.example.lorenzo.smartdonkeysapp.model.Message;

import java.io.Serializable;

public class BetMessage extends Message implements Serializable {

    private int cash;

    public BetMessage(int cash){
        super(MESSAGE_TYPE.BET, "");
        this.cash = cash;
    }

    public int getCash(){
        return cash;
    }
}
