package com.example.lorenzo.smartdonkeysapp;

import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MenuActivity extends AppCompatActivity {

    private Connection connection;
    private TextView welcomeMessage;
    private Button guardaSpot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*AlertDialog debugMessage = new AlertDialog.Builder(MenuActivity.this).create();
        debugMessage.setMessage("sei nell'activity menu");
        debugMessage.show();*/
        setContentView(R.layout.menu_activity);

        this.connection = (Connection) getIntent().getSerializableExtra("connection");
        welcomeMessage = findViewById(R.id.WelcomeMessage);
        guardaSpot = findViewById((R.id.guarda_spot));
        guardaSpot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                guardaSpotPubblicitario();
            }
        });
        welcomeMessage.setText("Benvenuto " + getIntent().getStringExtra("username"));
    }

    private void guardaSpotPubblicitario(){
        Intent intent = new Intent(getApplicationContext(), SpotActivity.class);
        intent.putExtra("connection", connection);
        startActivity(intent);
    }

}

