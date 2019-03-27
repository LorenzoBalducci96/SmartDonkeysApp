package com.example.lorenzo.smartdonkeysapp.model.poker;

import com.example.lorenzo.smartdonkeysapp.model.MESSAGE_TYPE;
import com.example.lorenzo.smartdonkeysapp.model.Message;

import java.io.Serializable;

public class Card extends Message implements Serializable, Comparable<Card> {

    private int number;
    private CARD_TYPE card_type;

    public Card(CARD_TYPE card_type, int number){
        super(MESSAGE_TYPE.CARD, "");
        this.number = number;
        this.card_type = card_type;
    }

    public int getNumber() {
        return number;
    }

    public CARD_TYPE getCard_type() {
        return card_type;
    }

    @Override
    public int compareTo(Card card) {
        return this.getNumber() - card.getNumber();
    }
}
