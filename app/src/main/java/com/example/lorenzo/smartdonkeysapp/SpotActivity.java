package com.example.lorenzo.smartdonkeysapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.lorenzo.smartdonkeysapp.model.Answer;
import com.example.lorenzo.smartdonkeysapp.model.MESSAGE_TYPE;
import com.example.lorenzo.smartdonkeysapp.model.Message;
import com.example.lorenzo.smartdonkeysapp.model.Result;
import com.example.lorenzo.smartdonkeysapp.model.Spot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SpotActivity extends AppCompatActivity implements View.OnClickListener{

    Connection connection;
    VideoView videoView;
    TextView domanda;
    Button opzione1;
    Button opzione2;
    Button opzione3;
    Button opzione4;
    int spotId;
    ProgressDialog progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_activity);
        progress = new ProgressDialog(this);

        this.connection = ConnectionHandler.getConnection();
        videoView = findViewById(R.id.videoView);
        domanda = findViewById(R.id.domanda);
        opzione1 = findViewById(R.id.opzione1);
        opzione2 = findViewById(R.id.opzione2);
        opzione3 = findViewById(R.id.opzione3);
        opzione4 = findViewById(R.id.opzione4);
        opzione1.setOnClickListener(this);
        opzione2.setOnClickListener(this);
        opzione3.setOnClickListener(this);
        opzione4.setOnClickListener(this);

        try {
            Message message = connection.getCachedMessage();

            if(message.getMessageCode().equals(MESSAGE_TYPE.SPOT)) {
                Spot spot = (Spot) message;
                File outputDir = getApplicationContext().getCacheDir(); // context being the Activity pointer
                File outputFile = File.createTempFile("prefix", "extension", outputDir);
                FileOutputStream stream = new FileOutputStream(outputFile.getAbsolutePath());
                stream.write(spot.getVideo());
                videoView.setVideoPath(outputFile.getAbsolutePath());
                domanda.setText(spot.getQuestion());
                opzione1.setText(spot.getAnswers().get(0));
                opzione2.setText(spot.getAnswers().get(1));
                opzione3.setText(spot.getAnswers().get(2));
                opzione4.setText(spot.getAnswers().get(3));
                spotId = spot.getSpotId();
                videoView.start();
            }
            else if(message.getMessageCode().equals(MESSAGE_TYPE.ERROR_MESSAGE)){
                AlertDialog alertDialog = new AlertDialog.Builder(SpotActivity.this).create();
                alertDialog.setMessage(message.getServiceMessage());
                alertDialog.show();
            }
            else{
                AlertDialog alertDialog = new AlertDialog.Builder(SpotActivity.this).create();
                alertDialog.setMessage("risposta inattesa dal server, ti preghiamo di riavviare l'applicazione");
                alertDialog.show();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Button button = findViewById(v.getId());
        String ris = button.getText().toString();
        Answer answer = new Answer(ris, spotId);
        progress.setTitle("Invio risposta");
        progress.setMessage("attendere l'esito della risposta...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        SendAnswer sendAnswer = new SendAnswer(answer);
        sendAnswer.execute();
    }

    public class SendAnswer extends AsyncTask<Void, Void, Result> {

        Answer answer;

        SendAnswer(Answer answer) {
            this.answer = answer;
        }

        @Override
        protected Result doInBackground(Void... voids) {
            return connection.sendAnswer(answer);
        }

        @Override
        protected void onPostExecute(final Result result) {
            progress.dismiss();
            /*
            AlertDialog alertDialog = new AlertDialog.Builder(SpotActivity.this).create();
            alertDialog.setMessage(result.getResult());
            alertDialog.show();

            finish();
            */
            AlertDialog.Builder builder = new AlertDialog.Builder(SpotActivity.this);
            builder.setMessage(result.getResult())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    }).create().show();
        }
    }
}

