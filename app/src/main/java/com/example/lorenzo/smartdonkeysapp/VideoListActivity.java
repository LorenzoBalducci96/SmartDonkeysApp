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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lorenzo.smartdonkeysapp.model.Answer;
import com.example.lorenzo.smartdonkeysapp.model.AvailableVideoInfo;
import com.example.lorenzo.smartdonkeysapp.model.MESSAGE_TYPE;
import com.example.lorenzo.smartdonkeysapp.model.Message;
import com.example.lorenzo.smartdonkeysapp.model.Result;
import com.example.lorenzo.smartdonkeysapp.model.Spot;
import com.example.lorenzo.smartdonkeysapp.model.SpotList;
import com.example.lorenzo.smartdonkeysapp.model.VideoListMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class VideoListActivity extends AppCompatActivity implements OnClickListener{

    Connection connection;
    ProgressDialog progress;
    LinearLayout layout;
    Button returnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_list_activity);
        layout = findViewById(R.id.linearLayout1);
        progress = new ProgressDialog(this);
        returnHome = findViewById(R.id.return_home);
        returnHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

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
        String requestedSpotId = (String) v.getTag();
        connection.setChoosedSpot(requestedSpotId);
        changeActivity();
    }

    public class RequestVideoList extends AsyncTask<Void, Void, Message> {

        Connection connection;

        RequestVideoList(Connection connection){
            this.connection = connection;
        }

        @Override
        protected Message doInBackground(Void... voids) {
            return connection.getSpotList();
        }

        @Override
        protected void onPostExecute(final Message message) {
            progress.dismiss();
            if(message.getMessageCode().equals(MESSAGE_TYPE.VIDEO_LIST_MESSAGE)){
                SpotList spotList = (SpotList) message;

                for(Spot spot : spotList.getSpotList()){
                    LayoutInflater inflater;
                    inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewSlotPubblicita = (View) inflater.inflate(R.layout.slot_video_pubblicitario, null);

                    LinearLayout.LayoutParams slot_spot_param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    );

                    slot_spot_param.height = 200;
                    slot_spot_param.setMargins(0, 10, 0, 0);
                    viewSlotPubblicita.setLayoutParams(slot_spot_param);

                    TextView titolo = viewSlotPubblicita.findViewById(R.id.titolo_spot_pubblicitario);
                    TextView descrizione = viewSlotPubblicita.findViewById(R.id.descrizione_spot_pubblicitario);
                    ImageView immagine = viewSlotPubblicita.findViewById(R.id.immagine_spot_pubblicitario);

                    titolo.setText(spot.getTitolo());
                    descrizione.setText(spot.getDescrizione());
                    new DownloadImageTask(immagine, spot.getImmagine()).execute();

                    viewSlotPubblicita.setTag(spot.getId());
                    viewSlotPubblicita.setOnClickListener(VideoListActivity.this);

                    layout.addView(viewSlotPubblicita);
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

    private class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {
        ImageView bmImage;
        String res;

        public DownloadImageTask(ImageView bmImage, String res) {
            this.bmImage = bmImage;
            this.res = res;
        }

        protected Bitmap doInBackground(Void...Voids) {

            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(res).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}

