package com.example.chatapp.model;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class ChatModel {
    private String msg;
    private String sender;
    private String receiver;
    public ChatModel() {
    }

    public ChatModel(String sender, String receiver, String msg) {
        this.sender = sender;
        this.receiver = receiver;
        this.msg = msg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    @NonNull
    @Override
    public String toString() {
        return "ChatModel{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", message='" + msg + '\'' +
                '}';
    }

    public Map<String, String> toHashMap(){
        HashMap<String,String> hm = new HashMap<>();
        hm.put("sender",sender);
        hm.put("receiver",receiver);
        hm.put("msg",msg);
        return hm;
    }
}
