package com.example.lorenzo.smartdonkeysapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.lorenzo.smartdonkeysapp.model.poker.CARD_TYPE;
import com.example.lorenzo.smartdonkeysapp.model.poker.Card;

public class CardBitmapService {
    private Context context;
    public CardBitmapService(Context context){
        this.context = context;
    }

    public Bitmap getBitmap(Card card){
        if(card.getCard_type().equals(CARD_TYPE.HEARTS)){
            if(card.getNumber() == 1)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.ace_of_hearts);
            if(card.getNumber() == 2)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.hearts2);
            if(card.getNumber() == 3)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.hearts3);
            if(card.getNumber() == 4)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.hearts4);
            if(card.getNumber() == 5)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.hearts5);
            if(card.getNumber() == 6)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.hearts6);
            if(card.getNumber() == 7)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.hearts7);
            if(card.getNumber() == 8)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.hearts8);
            if(card.getNumber() == 9)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.hearts9);
            if(card.getNumber() == 10)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.hearts10);
            if(card.getNumber() == 11)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.jack_of_hearts);
            if(card.getNumber() == 12)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.queen_of_hearts);
            if(card.getNumber() == 13)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.king_of_hearts);
        }
        if(card.getCard_type().equals(CARD_TYPE.CLUBS)){
            if(card.getNumber() == 1)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.ace_of_clubs);
            if(card.getNumber() == 2)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.clubs2);
            if(card.getNumber() == 3)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.clubs3);
            if(card.getNumber() == 4)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.clubs4);
            if(card.getNumber() == 5)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.clubs5);
            if(card.getNumber() == 6)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.clubs6);
            if(card.getNumber() == 7)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.clubs7);
            if(card.getNumber() == 8)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.clubs8);
            if(card.getNumber() == 9)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.clubs9);
            if(card.getNumber() == 10)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.clubs10);
            if(card.getNumber() == 11)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.jack_of_clubs);
            if(card.getNumber() == 12)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.queen_of_clubs);
            if(card.getNumber() == 13)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.king_of_clubs);
        }
        if(card.getCard_type().equals(CARD_TYPE.DIAMONDS)){
            if(card.getNumber() == 1)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.ace_of_diamonds);
            if(card.getNumber() == 2)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.diamonds2);
            if(card.getNumber() == 3)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.diamonds3);
            if(card.getNumber() == 4)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.diamonds4);
            if(card.getNumber() == 5)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.diamonds5);
            if(card.getNumber() == 6)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.diamonds6);
            if(card.getNumber() == 7)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.diamonds7);
            if(card.getNumber() == 8)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.diamonds8);
            if(card.getNumber() == 9)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.diamonds9);
            if(card.getNumber() == 10)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.diamonds10);
            if(card.getNumber() == 11)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.jack_of_diamonds);
            if(card.getNumber() == 12)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.queen_of_diamonds);
            if(card.getNumber() == 13)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.king_of_diamonds);
        }
        if(card.getCard_type().equals(CARD_TYPE.SPADES)){
            if(card.getNumber() == 1)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.ace_of_spades);
            if(card.getNumber() == 2)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.spades2);
            if(card.getNumber() == 3)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.spades3);
            if(card.getNumber() == 4)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.spades4);
            if(card.getNumber() == 5)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.spades5);
            if(card.getNumber() == 6)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.spades6);
            if(card.getNumber() == 7)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.spades7);
            if(card.getNumber() == 8)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.spades8);
            if(card.getNumber() == 9)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.spades9);
            if(card.getNumber() == 10)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.spades10);
            if(card.getNumber() == 11)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.jack_of_spades);
            if(card.getNumber() == 12)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.queen_of_spades);
            if(card.getNumber() == 13)
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.king_of_spades);
        }
        return null;
    }
}
