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
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lorenzo.smartdonkeysapp.model.Coupon;
import com.example.lorenzo.smartdonkeysapp.model.EsitoAcquisto;
import com.example.lorenzo.smartdonkeysapp.model.MESSAGE_TYPE;
import com.example.lorenzo.smartdonkeysapp.model.Mercatino;
import com.example.lorenzo.smartdonkeysapp.model.Message;

import java.io.InputStream;

public class MercatinoActivity extends AppCompatActivity implements View.OnClickListener {

    Connection connection;
    ProgressDialog progress;
    LinearLayout linearLayout1;
    TextView coins;
    Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mercatino_activity);
        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        progress = new ProgressDialog(this);
        coins = findViewById(R.id.coins_number);
        homeButton = findViewById(R.id.return_home);

        this.connection = ConnectionHandler.getConnection();
        coins.setText(String.valueOf(connection.getProfile().getCoins()));

        RequestMercatino downloadContent = new RequestMercatino();
        try{
            downloadContent.execute();
        }catch(Exception e){
            e.printStackTrace();
        }

        progress.setTitle("caricamento mercatino");
        progress.setMessage("per favore attendi il caricamento del mercatino...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }

    public void onClick(View v) {
        String nomeCoupon = (String) v.getTag();
        RequestAcquisto requestAcquisto = new RequestAcquisto(nomeCoupon);
        requestAcquisto.execute();
    }

    public class RequestMercatino extends AsyncTask<Void, Void, Message> {

        RequestMercatino(){
        }

        @Override
        protected Message doInBackground(Void... voids) {
            return connection.requestMercatino();
        }

        @Override
        protected void onPostExecute(final Message message) {

            if (message.getMessageCode().equals(MESSAGE_TYPE.MERCATINO)) {
                progress.dismiss();
                Mercatino mercatino = (Mercatino) message;

                int count = 0;
                for(Coupon coupon : mercatino.getListaCoupons()) {

                    /*
                    LayoutInflater inflater;
                    inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDeiCoupon = (View) inflater.inflate(R.layout.icona_mercatino , null);
                    //viewDeiCoupon.setClickable(true);
                    LinearLayout.LayoutParams slot_coupon_params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    slot_coupon_params.height = 200;
                    slot_coupon_params.setMargins(0, 10, 0, 0);
                    viewDeiCoupon.setLayoutParams(slot_coupon_params);

                    ImageView icona = viewDeiCoupon.findViewById(R.id.icona_coupon_mercatino_icona);
                    new DownloadImageTask(icona, coupon.getImmagine()).execute();

                    viewDeiCoupon.setTag(coupon.getTipologia());

                    count++;
                    viewDeiCoupon.setOnClickListener(MercatinoActivity.this);
                    linearLayout1.addView(viewDeiCoupon);
                    */



                    LayoutInflater inflater;
                    inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDeiCoupon = (View) inflater.inflate(R.layout.coupon_mercatino , null);
                    //viewDeiCoupon.setClickable(true);
                    LinearLayout.LayoutParams slot_coupon_params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    );
                    slot_coupon_params.height = 200;
                    slot_coupon_params.setMargins(0, 10, 0, 0);
                    viewDeiCoupon.setLayoutParams(slot_coupon_params);

                    TextView descrizione = viewDeiCoupon.findViewById(R.id.descrizione_coupon_mercatino);
                    descrizione.setText(coupon.getDescrizione());
                    ImageView icona = viewDeiCoupon.findViewById(R.id.icona_coupon_mercatino);
                    new DownloadImageTask(icona, coupon.getImmagine()).execute();
                    TextView costo = viewDeiCoupon.findViewById(R.id.costo_coupon_mercatino);
                    costo.setText(String.valueOf(coupon.getCosto()));

                    viewDeiCoupon.setTag(coupon.getNomeCoupon());

                    count++;
                    viewDeiCoupon.setOnClickListener(MercatinoActivity.this);
                    linearLayout1.addView(viewDeiCoupon);
                
                }
            }
            else if(message.getMessageCode().equals(MESSAGE_TYPE.ERROR_MESSAGE)){
                progress.dismiss();
                AlertDialog alertDialog = new AlertDialog.Builder(MercatinoActivity.this).create();
                alertDialog.setMessage(message.getServiceMessage());
                alertDialog.show();
            }
            else{
                progress.dismiss();
                AlertDialog alertDialog = new AlertDialog.Builder(MercatinoActivity.this).create();
                alertDialog.setMessage("risposta inattesa dal server ti invitiamo a riprovare");
                alertDialog.show();
            }
        }
    }

    public class RequestAcquisto extends AsyncTask<Void, Void, Message> {
        String nomeCouponRichiesto;
        RequestAcquisto(String nomeCouponRichiesto){
            this.nomeCouponRichiesto = nomeCouponRichiesto;
        }

        @Override
        protected Message doInBackground(Void... voids) {
            return connection.requestAcquisto(nomeCouponRichiesto);
        }

        @Override
        protected void onPostExecute(final Message message) {

            if (message.getMessageCode().equals(MESSAGE_TYPE.ESITO_ACQUISTO)) {
                progress.dismiss();
                EsitoAcquisto esitoAcquisto = (EsitoAcquisto) message;
                AlertDialog.Builder builder = new AlertDialog.Builder(MercatinoActivity.this);
                if(esitoAcquisto.getEsitoAcquisto()) {
                    builder.setMessage(esitoAcquisto.getMessaggio() + "codice: " + esitoAcquisto.getCouponCode())
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            }).create().show();
                    connection.updateCoins(-esitoAcquisto.getCosto());
                }else{
                    builder.setMessage(esitoAcquisto.getMessaggio())
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            }).create().show();
                }

            }
            else if(message.getMessageCode().equals(MESSAGE_TYPE.ERROR_MESSAGE)){
                progress.dismiss();
                AlertDialog alertDialog = new AlertDialog.Builder(MercatinoActivity.this).create();
                alertDialog.setMessage(message.getServiceMessage());
                alertDialog.show();
            }
            else{
                progress.dismiss();
                AlertDialog alertDialog = new AlertDialog.Builder(MercatinoActivity.this).create();
                alertDialog.setMessage("risposta inattesa dal server ti invitiamo a riprovare");
                alertDialog.show();
            }
        }
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

