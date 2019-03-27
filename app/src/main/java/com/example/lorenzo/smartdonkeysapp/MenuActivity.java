package com.example.lorenzo.smartdonkeysapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lorenzo.smartdonkeysapp.model.UserWelcomeMessage;

public class MenuActivity extends AppCompatActivity{

    private Connection connection;
    private TextView usernameLabel;
    private TextView welcomeMessage;
    private TextView coinsNumber;
    private ImageView profileImageIcon;
    private Button guardaSpot;
    //private Button visualizzaProfilo;
    private Button vaiAlMercatino;
    private Button bottoneGioca;
    private UserWelcomeMessage profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        this.connection = ConnectionHandler.getConnection();
        welcomeMessage = findViewById(R.id.WelcomeMessage);
        usernameLabel = findViewById(R.id.username_label);
        coinsNumber = findViewById(R.id.coins_number);
        profileImageIcon = findViewById(R.id.profile_image_icon);
        guardaSpot = findViewById((R.id.guarda_spot));
        guardaSpot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                guardaSpotPubblicitario();
            }
        });

        /*
        visualizzaProfilo = findViewById(R.id.visualizza_profilo);
        visualizzaProfilo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                visualizzaProfilo();
            }
        });
        */

        vaiAlMercatino = findViewById(R.id.mercatino);
        vaiAlMercatino.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                vaiAlMercatino();
            }
        });

        bottoneGioca = findViewById(R.id.gioca);
        bottoneGioca.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gioca();
            }
        });

        this.profile = (UserWelcomeMessage) connection.getProfile();

        this.usernameLabel.setText(profile.getUsername());
        this.welcomeMessage.setText(profile.getWelcomeMessage());
        this.coinsNumber.setText(String.valueOf(profile.getCoins()));
        Bitmap bitmap = BitmapFactory.decodeByteArray(profile.getProfileImage(), 0, profile.getProfileImage().length);
        this.profileImageIcon.setImageBitmap(bitmap);

    }


    private void visualizzaProfilo(){
        Intent intent = new Intent(getApplicationContext(), VisualizzaProfiloActivity.class);
        startActivity(intent);
    }

    private void guardaSpotPubblicitario(){
        Intent intent = new Intent(getApplicationContext(), VideoListActivity.class);
        startActivity(intent);
    }

    private void vaiAlMercatino(){
        Intent intent = new Intent(getApplicationContext(), MercatinoActivity.class);
        startActivity(intent);
    }

    private void gioca(){
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.profile = connection.getProfile();
        this.coinsNumber.setText(String.valueOf(this.profile.getCoins()));
    }
}

