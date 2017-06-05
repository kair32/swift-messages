package com.example.kirill.mess.Parsers;

import android.text.Editable;

import com.example.kirill.mess.Parsers.DataPars;

public class ChatMessage {

    private String whoSendMsg;
    private String text;
    private String data;
    private String text2;
    private String photo;
    public ChatMessage() {

    }
    public ChatMessage(String text, String data, String text2, String photo) {
        this.text = text;
        this.data = data;
        this.text2 = text2;
        this.photo = photo;
    }
    public ChatMessage(String text, String photo) {
        this.text = text;
        this.photo = photo;
    }

    public ChatMessage(String text, String text2, String photo) {
        this.text = text;
        this.text2 = text2;
        this.photo = photo;
    }

    public String getWhoSendMsg(){return whoSendMsg;}
    public void setWhoSendMsg(String whoSendMsg){this.whoSendMsg = whoSendMsg;}

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getText2() {
        return text2;
    }
    public void setText2(String text) {
        this.text2 = text;
    }

    public String getData(){
        DataPars pars = new DataPars(data);
        return pars.getData();
    }
    public void setData(String data){
        this.data = data;
    }
}