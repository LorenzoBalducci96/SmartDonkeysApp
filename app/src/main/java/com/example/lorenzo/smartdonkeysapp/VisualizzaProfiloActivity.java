package com.example.lorenzo.smartdonkeysapp;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lorenzo.smartdonkeysapp.model.MESSAGE_TYPE;
import com.example.lorenzo.smartdonkeysapp.model.Message;
import com.example.lorenzo.smartdonkeysapp.model.UserData;

public class VisualizzaProfiloActivity extends AppCompatActivity{

    Connection connection;
    ProgressDialog progress;
    TextView email;
    TextView username;
    TextView coins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizza_profilo);
        progress = new ProgressDialog(this);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        coins = findViewById(R.id.coins);

        this.connection = ConnectionHandler.getConnection();

        RequestUserData downloadContent = new RequestUserData(connection);
        try{
            downloadContent.execute();
        }catch(Exception e){
            e.printStackTrace();
        }

        progress.setTitle("caricamento");
        progress.setMessage("per favore attendi...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }


    public class RequestUserData extends AsyncTask<Void, Void, Message> {
        Connection connection;
        RequestUserData(Connection connection){
            this.connection = connection;
        }

        @Override
        protected Message doInBackground(Void... voids) {
            return connection.requestUserData();
        }

        @Override
        protected void onPostExecute(final Message message) {

            if (message.getMessageCode().equals(MESSAGE_TYPE.USER_DATA_MESSAGE)) {
                UserData userData = (UserData) message;
                email.setText(userData.getEmail());
                username.setText(userData.getUsername());
                coins.setText(String.valueOf(userData.getCoins()));
                progress.dismiss();
            }
            else if(message.getMessageCode().equals(MESSAGE_TYPE.ERROR_MESSAGE)){
                progress.dismiss();
                AlertDialog alertDialog = new AlertDialog.Builder(VisualizzaProfiloActivity.this).create();
                alertDialog.setMessage(message.getServiceMessage());
                alertDialog.show();
            }
            else{
                progress.dismiss();
                AlertDialog alertDialog = new AlertDialog.Builder(VisualizzaProfiloActivity.this).create();
                alertDialog.setMessage("risposta inattesa dal server ti invitiamo a riprovare");
                alertDialog.show();
            }
        }
    }
}

