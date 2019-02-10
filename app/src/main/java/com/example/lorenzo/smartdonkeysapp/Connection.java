package com.example.lorenzo.smartdonkeysapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

public class Connection implements Serializable {
    private String server_ip_address = "192.168.1.165"; //loopback ip address
    private int server_port = 5000;
    Socket socket = null;
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;
    private Spot spot;

    public Connection(){

    }

    public UserWelcome doLogin(String email, String password){
        try {
            LoginPacket loginPacket = new LoginPacket(email, password);
            //if(socket == null) {
                socket = new Socket(server_ip_address, server_port);
                os = new ObjectOutputStream(socket.getOutputStream());
                is = new ObjectInputStream(socket.getInputStream());
            //}
            os.writeObject(loginPacket);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            UserWelcome userWelcome = (UserWelcome) is.readObject();
            return userWelcome;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("errore nel protocollo di comunicazione");
            return new UserWelcome(false, "", "errore nel protocollo di comunicazione, per favore, riavvia l'applicazione");
        }
        return new UserWelcome(false, "", "errore imprevisto, per favore, riavvia l'applicazione");
    }

    public void requestDownloadData(){
        try {
            os.write(1);//code for request download data
            os.flush();
            this.spot = (Spot) is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int thereIsCachedSpot(){//return 0 if is downloading, -1 if there isn't news on server, 1 if there is
        if(this.spot == null)
            return 0;//downloading
        if(this.spot.getSpotId() == -1)
            return -1;
        else
            return this.spot.getSpotId();
    }

    public Spot requestSpot(){
        try {
            os.write(1);//code for request download data
            os.flush();
            this.spot = (Spot) is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this.spot;
    }

    public Result sendAnswer(Answer answer){
        try {
            os.writeObject(answer);
            return (Result) is.readObject();
        } catch (IOException e) {//non ha funzionato l'invio o ricezione del messaggio
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();//non ha funzionato il cast a Result
        }
        return new Result("ci sono stati problemi di connessione, riprova di nuovo");
    }

    public Spot getCachedSpot(){
        return this.spot;
    }

    //public Spot requestSpot(){
      //  return new Spot();
    //}

    public void sendMessage(String string){

    }

    public void readMessage(String string){

    }
}
