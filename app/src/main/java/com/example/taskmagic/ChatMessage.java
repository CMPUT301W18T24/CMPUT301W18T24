/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import java.util.Date;

public class ChatMessage {
    private String message;
    private String senderId;
    private String receiverId;
    private String receiverName;
    private String senderName;
    private String date;

    public ChatMessage(){}


    public ChatMessage(String message,String senderId, String receiverId,String date,String receiverName,String senderName){
        this.message=message;
        this.senderId=senderId;
        this.receiverId=receiverId;
        this.receiverName=receiverName;
        this.date=date;
        this.senderName=senderName;

    }

    /**
     * This method returns the sender's name
     * @return senderName
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * This method takes a String and sets it as the sender's name
     * @param senderName
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * This method returns the receiver's name
     * @return
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * This method takes a String and sets it as the reciever's name
     * @param receiverName
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     * This method gets the date of the message as a String
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * Thi method takes a String and set it to be the date of the message
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * This method retrurns the bodyText of the message
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * This method takes a String and sets it to message
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * This method returns Id of the sender
     * @return
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * This method take a String and set it to senderId
     * @param senderId
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     * This method returns the reciever's id
     * @return
     */
    public String getReceiverId() {
        return receiverId;
    }

    /**
     * This method takes a String and sets it as the reciever's id
     * @param receiverId
     */
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}
