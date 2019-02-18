package com.example.lorenzo.smartdonkeysapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lorenzo.smartdonkeysapp.model.Answer;
import com.example.lorenzo.smartdonkeysapp.model.AvailableVideoInfo;
import com.example.lorenzo.smartdonkeysapp.model.MESSAGE_TYPE;
import com.example.lorenzo.smartdonkeysapp.model.Message;
import com.example.lorenzo.smartdonkeysapp.model.Result;
import com.example.lorenzo.smartdonkeysapp.model.Spot;
import com.example.lorenzo.smartdonkeysapp.model.VideoListMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class VideoListActivity extends AppCompatActivity implements OnClickListener{

    Connection connection;
    ProgressDialog progress;
    ImageView[] imageView = new ImageView[4];

    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_list_activity);
        imageView[0] = findViewById(R.id.imageView1);
        imageView[0].setOnClickListener(this);
        imageView[1] = findViewById(R.id.imageView2);
        imageView[1].setOnClickListener(this);
        imageView[2] = findViewById(R.id.imageView3);
        imageView[2].setOnClickListener(this);
        imageView[3] = findViewById(R.id.imageView4);
        imageView[3].setOnClickListener(this);
        progress = new ProgressDialog(this);

        this.connection = ConnectionHandler.getConnection();

        RequestVideoList downloadContent = new RequestVideoList(connection);
        try{
            downloadContent.execute();
        }catch(Exception e){
            e.printStackTrace();
        }

        progress.setTitle("Download");
        progress.setMessage("per favore, attendi il download della lista dei video...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }

    @Override
    public void onClick(View v) {
        ImageView imageView = findViewById(v.getId());
        String requestedSpotId = (String) imageView.getTag();
        progress.setTitle("download");
        progress.setMessage("attendere il download dello spot pubblicitario...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        SendVideoRequest sendVideoRequest = new SendVideoRequest(requestedSpotId);
        sendVideoRequest.execute();
    }

    public class SendVideoRequest extends AsyncTask<Void, Void, Boolean> {

        String requestedSpotId;

        SendVideoRequest(String requestedSpotId) {
            this.requestedSpotId = requestedSpotId;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return connection.requestSpot(requestedSpotId);
        }

        @Override
        protected void onPostExecute(final Boolean availability) {
            progress.dismiss();
            if(availability){
                changeActivity();
            }
            else{
                AlertDialog alertDialog = new AlertDialog.Builder(VideoListActivity.this).create();
                alertDialog.setMessage("errore imprevisto nella ricezione dello spot dal server");
                alertDialog.show();
            }
        }
    }

    public class RequestVideoList extends AsyncTask<Void, Void, Message> {

        Connection connection;

        RequestVideoList(Connection connection){
            this.connection = connection;
        }

        @Override
        protected Message doInBackground(Void... voids) {
            return connection.requestVideoList();
        }

        @Override
        protected void onPostExecute(final Message message) {
            progress.dismiss();
            if(message.getMessageCode().equals(MESSAGE_TYPE.VIDEO_LIST_MESSAGE)){
                VideoListMessage videoListMessage = (VideoListMessage) message;

                int count = 0;
                for (AvailableVideoInfo videoInfo : videoListMessage.getListaVideoDisponibili()) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(videoInfo.getImage(), 0, videoInfo.getImage().length);
                    imageView[count].setImageBitmap(bitmap);
                    imageView[count].setClickable(true);
                    imageView[count].setTag(videoInfo.getIdVideo());
                    count++;
                }
            }
            else if(message.getMessageCode() == MESSAGE_TYPE.ERROR_MESSAGE){
                AlertDialog alertDialog = new AlertDialog.Builder(VideoListActivity.this).create();
                alertDialog.setMessage(message.getServiceMessage());
                alertDialog.show();
            }
            else {
                AlertDialog alertDialog = new AlertDialog.Builder(VideoListActivity.this).create();
                alertDialog.setMessage("risposta inattesa dal server, ti invitiamo a riprovare");
                alertDialog.show();
            }
            return;
        }
    }

    private void changeActivity(){
        Intent intent = new Intent(getApplicationContext(), SpotActivity.class);
        startActivity(intent);
        finish();
    }

}

