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
import android.widget.VideoView;

import java.io.IOException;

public class SpotActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    Connection connection;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_activity);

        this.connection = (Connection) getIntent().getSerializableExtra("connection");

        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video01);
        videoView.start();
        Spot spotRicevuto = connection.requestSpot();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

