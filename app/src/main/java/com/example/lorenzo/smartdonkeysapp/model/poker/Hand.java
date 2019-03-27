package com.example.lorenzo.smartdonkeysapp.model.poker;

import com.example.lorenzo.smartdonkeysapp.model.MESSAGE_TYPE;
import com.example.lorenzo.smartdonkeysapp.model.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hand extends Message implements Serializable {
    private List<Card> hand;

    public Hand(){
        super(MESSAGE_TYPE.HAND, "");
        this.hand = new ArrayList<Card>();
    }

    public void insertCard(Card card){
        this.hand.add(card);
    }

    public List<Card> getHand(){
        return this.hand;
    }

    public boolean removeCard(Card card){
        for(Card card_i : hand){
            if(card_i.getNumber() == card.getNumber() &&
                    card_i.getCard_type().equals(card.getCard_type())){
                hand.remove(card_i);
                return true;
            }
        }
        return false;
    }

}
