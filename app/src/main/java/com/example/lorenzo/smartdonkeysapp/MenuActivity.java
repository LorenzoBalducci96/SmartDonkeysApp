package com.example.lorenzo.smartdonkeysapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private Connection connection;
    private TextView welcomeMessage;
    private Button guardaSpot;
    private Button visualizzaProfilo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        this.connection = ConnectionHandler.getConnection();
        welcomeMessage = findViewById(R.id.WelcomeMessage);
        guardaSpot = findViewById((R.id.guarda_spot));
        guardaSpot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                guardaSpotPubblicitario();
            }
        });

        visualizzaProfilo = findViewById(R.id.visualizza_profilo);
        visualizzaProfilo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                visualizzaProfilo();
            }
        });

        welcomeMessage.setText(getIntent().getStringExtra("welcome_message"));
    }

    private void visualizzaProfilo(){
        Intent intent = new Intent(getApplicationContext(), VisualizzaProfiloActivity.class);
        startActivity(intent);
    }

    private void guardaSpotPubblicitario(){
        Intent intent = new Intent(getApplicationContext(), VideoListActivity.class);
        startActivity(intent);
    }
}

