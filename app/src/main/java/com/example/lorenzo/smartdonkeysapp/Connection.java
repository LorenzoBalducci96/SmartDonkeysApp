package com.example.lorenzo.smartdonkeysapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.example.lorenzo.smartdonkeysapp.model.AcquistoRequest;
import com.example.lorenzo.smartdonkeysapp.model.Answer;
import com.example.lorenzo.smartdonkeysapp.model.Coupon;
import com.example.lorenzo.smartdonkeysapp.model.EsitoAcquisto;
import com.example.lorenzo.smartdonkeysapp.model.LoginMessage;
import com.example.lorenzo.smartdonkeysapp.model.LotteryTimeout;
import com.example.lorenzo.smartdonkeysapp.model.MESSAGE_TYPE;
import com.example.lorenzo.smartdonkeysapp.model.Mercatino;
import com.example.lorenzo.smartdonkeysapp.model.Message;
import com.example.lorenzo.smartdonkeysapp.model.Result;
import com.example.lorenzo.smartdonkeysapp.model.Spot;
import com.example.lorenzo.smartdonkeysapp.model.SpotList;
import com.example.lorenzo.smartdonkeysapp.model.UserData;
import com.example.lorenzo.smartdonkeysapp.model.UserWelcomeMessage;
import com.example.lorenzo.smartdonkeysapp.model.VideoListMessage;
import com.example.lorenzo.smartdonkeysapp.model.VideoRequestMessage;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class Connection implements Serializable {
    private String server_ip_address = "80.211.59.134";//"93.41.39.42"; //loopback ip address
    //private String host = "https://smartworld.cloud/DEBUGGING/";
    private String host = "http://192.168.43.72:8080/smartDonkeysWebSite/";
    public static String cookie = null;


    private int server_game_port = 5001;
    Socket socketGame = null;
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;
    private ObjectOutputStream osGame = null;
    private ObjectInputStream isGame = null;
    private Spot choosedSpot;
    private UserWelcomeMessage profile;
    private List<Spot> spotList;
    private String email;
    private String password;

    public Connection(){

    }

    public Connection(String serverIp){
        server_ip_address = serverIp;
    }

    public Message doLogin(String email, String password){
        try {
            URL url = new URL(host + "appLoginServlet");
            // 1. create HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            // 2. build JSON object
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("email", email);
            jsonObject.accumulate("password",  password);

            System.out.println(jsonObject.toString());

            // 3. add JSON content to POST request body
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonObject.toString());
            //Log.i(MainActivity.class.toString(), jsonObject.toString());
            writer.flush();
            writer.close();
            os.close();

            // 4. make POST request to the given URL
            conn.connect();

            //--------------------read response from here

            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String result = IOUtils.toString(in, "UTF-8");
            //String result = "";
            //String line = "";
            //while((result = reader.readLine()) != null)
              //  result += line;
            System.out.println("result after Reading JSON Response:");
            System.out.println(result);
            JSONObject myResponse = new JSONObject(result);
            in.close();
            cookie = conn.getHeaderField("Set-Cookie");
            conn.disconnect();
            if(myResponse.getInt("status_code") == 200) {
                UserWelcomeMessage userWelcomeMessage = new UserWelcomeMessage(
                        myResponse.getString("username"),
                        myResponse.getString("profile_image"),
                        myResponse.getString("welcome_message"),
                        myResponse.getInt("coins"),
                        myResponse.getInt("tickets"));
                Long countdownLottery = myResponse.getLong("countdown_lottery");
                if(countdownLottery != null)
                    userWelcomeMessage.setCountdownLottery(countdownLottery);
                this.profile = userWelcomeMessage;
                this.email = email;
                this.password = password;
                return userWelcomeMessage;
            }
            else if (myResponse.getInt("status_code") == 401){
                return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "email o password errate");
            }//else if (myResponse.getInt("status_code") == 500){ ...internal server error...
        } catch(Exception e) {
            return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "errore imprevisto, controlla la connessione e riavvia l'applicazione");
        }
        return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "errore imprevisto, controlla la connessione e riavvia l'applicazione");
    }


    public Message doRequestRegistration(String email, String password, String username, String photo){
        try {
            URL url = new URL(host + "registrationServlet");
            // 1. create HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            // 2. build JSON object
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("email", email);
            jsonObject.accumulate("password",  password);
            jsonObject.accumulate("username",  username);
            jsonObject.accumulate("profile_image",  photo);

            System.out.println(jsonObject.toString());

            // 3. add JSON content to POST request body
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonObject.toString());
            //Log.i(MainActivity.class.toString(), jsonObject.toString());
            writer.flush();
            writer.close();
            os.close();

            // 4. make POST request to the given URL
            conn.connect();

            //--------------------read response from here

            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String result = IOUtils.toString(in, "UTF-8");
            //String result = "";
            //String line = "";
            //while((result = reader.readLine()) != null)
            //  result += line;
            System.out.println("result after Reading JSON Response:");
            System.out.println(result);
            JSONObject myResponse = new JSONObject(result);
            in.close();

            conn.disconnect();
            if(myResponse.getInt("status_code") == 200){
                return new Message(MESSAGE_TYPE.OK, "");
            }
            else if (myResponse.getInt("status_code") == 500){
                return new Message(MESSAGE_TYPE.ERROR_MESSAGE, myResponse.getString("message"));
            }//else if (myResponse.getInt("status_code") == 500){ ...internal server error...
        } catch(Exception e) {
            return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "errore imprevisto, controlla la connessione e riavvia l'applicazione");
        }
        return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "errore imprevisto, controlla la connessione e riavvia l'applicazione");

    }

    public Message getSpotList(){
        try {
            URL url = new URL(host +"spotServlet");
            // 1. create HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Cookie",cookie);
            // 2. build JSON object
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("request", "video_list");

            System.out.println(jsonObject.toString());

            // 3. add JSON content to POST request body
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonObject.toString());
            //Log.i(MainActivity.class.toString(), jsonObject.toString());
            writer.flush();
            writer.close();
            os.close();

            // 4. make POST request to the given URL
            conn.connect();

            //--------------------read response from here

            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String result = IOUtils.toString(in, "UTF-8");
            System.out.println("result after Reading JSON Response:");
            System.out.println(result);
            JSONArray jsonResponse = new JSONArray(result);
            in.close();
            conn.disconnect();
            //tutti gli spot
            List<Spot> spotList = new ArrayList<Spot>();
            for(int n = 0; n < jsonResponse.length(); n++) {
                JSONObject JsonSpot = jsonResponse.getJSONObject(n);
                JSONArray jsonOpzioni = JsonSpot.getJSONArray("opzioni_risposta");
                List<String> listaOpzioni = new ArrayList<String>();
                for(int i = 0; i < jsonOpzioni.length(); i++){
                    listaOpzioni.add(jsonOpzioni.getString(i));
                    //String opzione = oggetto.getString("value");
                    //listaOpzioni.add(opzione);
                }
                Spot spot = new Spot(JsonSpot.getString("spot_id"),
                        JsonSpot.getString("titolo"),
                        JsonSpot.getString("descrizione"),
                        JsonSpot.getString("immagine"),
                        JsonSpot.getString("domanda"),
                        listaOpzioni,
                        JsonSpot.getString("video"));
                spotList.add(spot);
            }
            this.spotList = spotList;
            return new SpotList(spotList);
        } catch(Exception e) {
            return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "errore imprevisto, controlla la connessione e riavvia l'applicazione");
        }
    }

    public Message requestUserData(){
        try {
            os.writeObject(new Message(MESSAGE_TYPE.REQUEST_USER_DATA, ""));//code for request user data
            os.flush();
            Message userData = (Message) is.readObject();
            return userData;
        } catch (IOException e) {
            e.printStackTrace();
            return new Message(MESSAGE_TYPE.ERROR_MESSAGE,"errore nella comunicazione con il server, ti invitiamo a riprovare");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new Message(MESSAGE_TYPE.ERROR_MESSAGE,"errore inatteso dal server, ti invitiamo a riprovare");
        }
    }

    public boolean setChoosedSpot(String spotId){
        boolean found = false;
        for(Spot spot : spotList){
            if (spot.getId().equals(spotId)){
                this.choosedSpot = spot;
                found = true;
            }
        }
        return found;
    }


    public Spot getChoosedSpot() {
        return choosedSpot;
    }

    public UserWelcomeMessage getProfile(){
        return this.profile;
    }

    public Message sendAnswer(Answer answer){
        try {
            URL url = new URL(host + "answerServlet");
            // 1. create HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Cookie",cookie);
            // 2. build JSON object
            JSONObject jsonObject = new JSONObject();

            jsonObject.accumulate("spot_id", answer.getSpotId());
            jsonObject.accumulate("answer", answer.getAnswer());

            System.out.println(jsonObject.toString());

            // 3. add JSON content to POST request body
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonObject.toString());
            //Log.i(MainActivity.class.toString(), jsonObject.toString());
            writer.flush();
            writer.close();
            os.close();

            // 4. make POST request to the given URL
            conn.connect();

            //--------------------read response from here
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String result = IOUtils.toString(in, "UTF-8");
            System.out.println("result after Reading JSON Response:");
            System.out.println(result);
            JSONObject jsonResponse = new JSONObject(result);
            in.close();
            conn.disconnect();
            //tutti gli spot
            return new Result(jsonResponse.getString("message"),
                    jsonResponse.getInt("earned_coins"));
        } catch(Exception e) {
            return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "errore imprevisto, controlla la connessione e riavvia l'applicazione");
        }
    }

    public Message requestMercatino() {
        try {
            URL url = new URL(host + "mercatinoServlet");
            // 1. create HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Cookie",cookie);
            // 4. make POST request to the given URL
            conn.connect();

            //--------------------read response from here
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String result = IOUtils.toString(in, "UTF-8");
            System.out.println("result after Reading JSON Response:");
            System.out.println(result);
            JSONArray jsonResponse = new JSONArray(result);
            in.close();
            conn.disconnect();
            //tutti gli spot
            List<Coupon> couponList = new ArrayList<Coupon>();
            for(int n = 0; n < jsonResponse.length(); n++) {
                JSONObject JsonCoupon = jsonResponse.getJSONObject(n);

                Coupon coupon = new Coupon(JsonCoupon.getString("tipologia"),
                            JsonCoupon.getString("nome_coupon"),
                            JsonCoupon.getString("premio"),
                            JsonCoupon.getString("descrizione"),
                            JsonCoupon.getInt("costo"),
                            JsonCoupon.getString("immagine"));
                couponList.add(coupon);
            }
            return new Mercatino(couponList);
        } catch(Exception e) {
            return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "errore imprevisto, controlla la connessione e riavvia l'applicazione");
        }
    }

    public Message requestAcquisto(String nomeCouponRichiesto) {
        try {
            URL url = new URL(host + "shopServlet");
            // 1. create HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Cookie",cookie);
            // 2. build JSON object
            JSONObject jsonObject = new JSONObject();

            jsonObject.accumulate("nome_coupon", nomeCouponRichiesto);

            System.out.println(jsonObject.toString());

            // 3. add JSON content to POST request body
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonObject.toString());
            //Log.i(MainActivity.class.toString(), jsonObject.toString());
            writer.flush();
            writer.close();
            os.close();

            // 4. make POST request to the given URL
            conn.connect();

            //--------------------read response from here
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String result = IOUtils.toString(in, "UTF-8");
            System.out.println("result after Reading JSON Response:");
            System.out.println(result);
            JSONObject jsonResponse = new JSONObject(result);
            in.close();
            conn.disconnect();
            //tutti gli spot
            if(jsonResponse.getInt("status_code") == 200){
                return new EsitoAcquisto(jsonResponse.getString("message"),
                        true, jsonResponse.getString("codice"),
                        jsonResponse.getInt("costo"));
            }
            else{
                return new EsitoAcquisto(jsonResponse.getString("message"),false, "", 0);
            }
        } catch(Exception e) {
            return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "errore imprevisto, controlla la connessione e riavvia l'applicazione");
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void updateCoins(int earnedCoins){
        this.profile.updateCoins(earnedCoins);
    }

    public void updateTickets(int earnedTickets){
        this.profile.updateTickets(earnedTickets);
    }

    public Message connectGame(){
        try {
            socketGame = new Socket(server_ip_address, server_game_port);
            osGame = new ObjectOutputStream(socketGame.getOutputStream());
            isGame = new ObjectInputStream(socketGame.getInputStream());
            osGame.writeObject(new LoginMessage(email, password));
            Message message = (Message) isGame.readObject();
            return message;
        } catch (IOException e) {
            e.printStackTrace();
            return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "errore nella comunicazione con il server");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "errore nella comunicazione con il server");
        }
    }

    public Message getTimeoutLottery(){
        try {
            URL url = new URL(host + "nextLotteryServlet");
            // 1. create HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Cookie",cookie);
            // 4. make POST request to the given URL
            conn.connect();

            //--------------------read response from here
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String result = IOUtils.toString(in, "UTF-8");
            System.out.println("result after Reading JSON Response:");
            System.out.println(result);
            JSONObject jsonResponse = new JSONObject(result);
            in.close();
            conn.disconnect();

            long remaining_seconds = jsonResponse.getLong("remaining_seconds");

            return new LotteryTimeout(remaining_seconds);
        } catch(Exception e) {
            return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "errore imprevisto, controlla la connessione e riavvia l'applicazione");
        }
    }
}
