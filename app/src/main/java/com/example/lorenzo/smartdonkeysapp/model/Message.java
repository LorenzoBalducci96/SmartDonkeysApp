package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;

public class Message implements Serializable {
    private MESSAGE_TYPE messageType;
    private String serviceMessage;

    public Message(MESSAGE_TYPE messageType, String serviceMessage){
        this.messageType = messageType;
        this.serviceMessage = serviceMessage;
    }

    public MESSAGE_TYPE getMessageCode() {
        return messageType;
    }

    public String getServiceMessage() {
        return serviceMessage;
    }
}