package com.example.lorenzo.smartdonkeysapp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import com.example.lorenzo.smartdonkeysapp.model.AcquistoRequest;
import com.example.lorenzo.smartdonkeysapp.model.Answer;
import com.example.lorenzo.smartdonkeysapp.model.LoginMessage;
import com.example.lorenzo.smartdonkeysapp.model.MESSAGE_TYPE;
import com.example.lorenzo.smartdonkeysapp.model.Message;
import com.example.lorenzo.smartdonkeysapp.model.Result;
import com.example.lorenzo.smartdonkeysapp.model.Spot;
import com.example.lorenzo.smartdonkeysapp.model.UserData;
import com.example.lorenzo.smartdonkeysapp.model.UserWelcomeMessage;
import com.example.lorenzo.smartdonkeysapp.model.VideoListMessage;
import com.example.lorenzo.smartdonkeysapp.model.VideoRequestMessage;

public class Connection implements Serializable {
    private String server_ip_address = "80.211.59.134";//"93.41.39.42"; //loopback ip address
    private int server_port = 5000;
    private int server_game_port = 5001;
    Socket socket = null;
    Socket socketGame = null;
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;
    private ObjectOutputStream osGame = null;
    private ObjectInputStream isGame = null;
    private Message cachedMessage;
    private UserWelcomeMessage profile;
    private String email;
    private String password;

    public Connection(){

    }

    public Connection(String serverIp){
        server_ip_address = serverIp;
    }

    public Message doLogin(String email, String password){
        try {
            LoginMessage loginMessage = new LoginMessage(email, password);
            //if(socket == null) {
                socket = new Socket(server_ip_address, server_port);
                os = new ObjectOutputStream(socket.getOutputStream());
                is = new ObjectInputStream(socket.getInputStream());
            //}
            this.email = email;
            this.password = password;

            os.writeObject(loginMessage);
            os.flush();
        } catch (IOException e) {
            return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "errore imprevisto, controlla la connessione e riavvia l'applicazione");
        }

        try {
            Message message = (Message) is.readObject();
            if(message.getMessageCode().equals(MESSAGE_TYPE.USER_WELCOME_MESSAGE))
                this.profile = (UserWelcomeMessage) message;
            return message;
        }  catch (Exception e) {
            return new Message(MESSAGE_TYPE.ERROR_MESSAGE,"errore imprevisto, per favore, riavvia l'applicazione");
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

    public Message requestVideoList() {
        try {
            os.writeObject(new Message(MESSAGE_TYPE.REQUEST_VIDEO_LIST, ""));
            os.flush();
            Message message = (Message) is.readObject();
            return message;
        } catch (IOException e) {
            e.printStackTrace();
            return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "errore nella comunicazione con il server");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "errore nella comunicazione con il server");
        }
    }

    public Boolean requestSpot(String spotId){
        VideoRequestMessage videoRequestMessage = new VideoRequestMessage(spotId);
        try {
            os.writeObject(videoRequestMessage);
            os.flush();
            cachedMessage = (Message) is.readObject();
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }catch(ClassNotFoundException e){
            e.printStackTrace();
            return false;
        }
    }


    public Message getCachedMessage() {
        return cachedMessage;
    }

    public UserWelcomeMessage getProfile(){
        return this.profile;
    }

    public Message sendAnswer(Answer answer){
        try {
            os.writeObject(answer);
            os.flush();
            return (Message) is.readObject();
        } catch (IOException e) {//non ha funzionato l'invio o ricezione del messaggio
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();//non ha funzionato il cast a Result
        }
        return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "ci sono stati problemi di connessione, riprova di nuovo");
    }

    public Message requestMercatino() {
        try {
            os.writeObject(new Message(MESSAGE_TYPE.MERCATINO_REQUEST, ""));
            os.flush();
            Message message = (Message) is.readObject();
            return message;
        } catch (IOException e) {//non ha funzionato l'invio o ricezione del messaggio
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();//non ha funzionato il cast a Result
        }
        return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "ci sono stati dei problemi di connessione");
    }

    public Message requestAcquisto(String nomeCouponRichiesto) {
        try {
            os.writeObject(new AcquistoRequest(nomeCouponRichiesto));
            os.flush();
            Message message = (Message) is.readObject();
            return message;
        } catch (IOException e) {//non ha funzionato l'invio o ricezione del messaggio
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();//non ha funzionato il cast a Result
        }
        return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "ci sono stati dei problemi di connessione");

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
}
