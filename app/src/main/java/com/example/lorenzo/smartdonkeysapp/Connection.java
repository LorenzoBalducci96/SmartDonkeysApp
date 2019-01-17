package com.example.lorenzo.smartdonkeysapp;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class Connection implements Serializable {
    private String server_ip_address = "192.168.1.165"; //loopback ip address
    private int server_port = 6788;

    public Connection() throws IOException {
        new Thread(new Runnable(){
            public void run(){
                try {
                    Socket socket = new Socket(server_ip_address, server_port);
                    socket.getOutputStream().write(55);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //Socket socket = new Socket(server_ip_address, server_port);
    }

    public LoginPacket doLogin(String email, String password){
        return new LoginPacket(true, "Mario");
    }

    public Spot requestSpot(){
        return new Spot();
    }

    public void sendMessage(String string){

    }

    public void readMessage(String string){

    }
}
