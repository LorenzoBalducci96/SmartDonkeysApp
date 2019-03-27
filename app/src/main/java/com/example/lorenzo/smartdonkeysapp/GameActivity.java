package com.example.lorenzo.smartdonkeysapp;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lorenzo.smartdonkeysapp.model.Answer;
import com.example.lorenzo.smartdonkeysapp.model.Coupon;
import com.example.lorenzo.smartdonkeysapp.model.EsitoAcquisto;
import com.example.lorenzo.smartdonkeysapp.model.MESSAGE_TYPE;
import com.example.lorenzo.smartdonkeysapp.model.Mercatino;
import com.example.lorenzo.smartdonkeysapp.model.Message;
import com.example.lorenzo.smartdonkeysapp.model.Result;
import com.example.lorenzo.smartdonkeysapp.model.poker.Card;
import com.example.lorenzo.smartdonkeysapp.model.poker.Hand;

import java.net.Socket;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    Connection connection;
    ProgressDialog progress;
    CardBitmapService cardBitmapService;
    ImageView[] cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        progress = new ProgressDialog(this);
        cardBitmapService = new CardBitmapService(getApplicationContext());
        cards = new ImageView[5];
        cards[0] = findViewById(R.id.card1);
        cards[1] = findViewById(R.id.card2);
        cards[2] = findViewById(R.id.card3);
        cards[3] = findViewById(R.id.card4);
        cards[4] = findViewById(R.id.card5);

        this.connection = ConnectionHandler.getConnection();

        SendGameRequest sendGameRequest = new SendGameRequest();
        sendGameRequest.execute();

        progress.setTitle("matching giocatori");
        progress.setMessage("matching giocatori ti prego attendi...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }

    public class SendGameRequest extends AsyncTask<Void, Void, Message> {

        SendGameRequest() {

        }

        @Override
        protected Message doInBackground(Void... voids) {
            return connection.connectGame();
        }

        @Override
        protected void onPostExecute(final Message message) {
            progress.dismiss();
            if(message.getMessageCode().equals(MESSAGE_TYPE.ERROR_MESSAGE)){
                AlertDialog alertDialog = new AlertDialog.Builder(GameActivity.this).create();
                alertDialog.setMessage(message.getServiceMessage());
                alertDialog.show();
            }else if(message.getMessageCode().equals(MESSAGE_TYPE.HAND)){
                Hand hand = (Hand) message;
                int count = 0;
                for(Card card : hand.getHand()){
                    cards[count].setImageBitmap(cardBitmapService.getBitmap(card));
                    count++;
                }
            }
        }
    }
}

