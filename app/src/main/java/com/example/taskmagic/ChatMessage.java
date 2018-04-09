/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import java.util.Date;

/**
 * This Class is responsible for holding a users chat message
 *
 */

public class ChatMessage {
    private String message;
    private String senderId;
    private String receiverId;
    private String receiverName;
    private String senderName;
    private String date;


    public ChatMessage(){}

    /**
     * Initializing the chat message variables
     * @param message
     * @param senderId
     * @param receiverId
     * @param date
     * @param receiverName
     * @param senderName
     */
    public ChatMessage(String message,String senderId, String receiverId,String date,String receiverName,String senderName){
        this.message=message;
        this.senderId=senderId;
        this.receiverId=receiverId;
        this.receiverName=receiverName;
        this.date=date;
        this.senderName=senderName;

    }

    /**
     *
     * @return String
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     *
     * @param senderName
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     *
     * @return receiverName
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     *
     * @param receiverName
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     *
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     *
     * @param senderId
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     *
     * @return receiverId
     */
    public String getReceiverId() {
        return receiverId;
    }

    /**
     *
     * @param receiverId
     */
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}
