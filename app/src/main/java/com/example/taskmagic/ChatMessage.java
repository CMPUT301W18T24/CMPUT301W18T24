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
<<<<<<< HEAD
     *
     * @return String
=======
     * This method returns the sender's name
     * @return senderName
>>>>>>> 9bd5af42e2e58e237d0529a15984f22eb568be60
     */
    public String getSenderName() {
        return senderName;
    }

    /**
<<<<<<< HEAD
     *
=======
     * This method takes a String and sets it as the sender's name
>>>>>>> 9bd5af42e2e58e237d0529a15984f22eb568be60
     * @param senderName
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
<<<<<<< HEAD
     *
     * @return receiverName
=======
     * This method returns the receiver's name
     * @return
>>>>>>> 9bd5af42e2e58e237d0529a15984f22eb568be60
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
<<<<<<< HEAD
     *
=======
     * This method takes a String and sets it as the reciever's name
>>>>>>> 9bd5af42e2e58e237d0529a15984f22eb568be60
     * @param receiverName
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
<<<<<<< HEAD
     *
     * @return date
=======
     * This method gets the date of the message as a String
     * @return
>>>>>>> 9bd5af42e2e58e237d0529a15984f22eb568be60
     */
    public String getDate() {
        return date;
    }

    /**
<<<<<<< HEAD
     *
=======
     * Thi method takes a String and set it to be the date of the message
>>>>>>> 9bd5af42e2e58e237d0529a15984f22eb568be60
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
<<<<<<< HEAD
     *
     * @return message
=======
     * This method retrurns the bodyText of the message
     * @return
>>>>>>> 9bd5af42e2e58e237d0529a15984f22eb568be60
     */
    public String getMessage() {
        return message;
    }

    /**
<<<<<<< HEAD
     *
=======
     * This method takes a String and sets it to message
>>>>>>> 9bd5af42e2e58e237d0529a15984f22eb568be60
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
<<<<<<< HEAD
     *
=======
     * This method returns Id of the sender
>>>>>>> 9bd5af42e2e58e237d0529a15984f22eb568be60
     * @return
     */
    public String getSenderId() {
        return senderId;
    }

    /**
<<<<<<< HEAD
     *
=======
     * This method take a String and set it to senderId
>>>>>>> 9bd5af42e2e58e237d0529a15984f22eb568be60
     * @param senderId
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
<<<<<<< HEAD
     *
     * @return receiverId
=======
     * This method returns the reciever's id
     * @return
>>>>>>> 9bd5af42e2e58e237d0529a15984f22eb568be60
     */
    public String getReceiverId() {
        return receiverId;
    }

    /**
<<<<<<< HEAD
     *
=======
     * This method takes a String and sets it as the reciever's id
>>>>>>> 9bd5af42e2e58e237d0529a15984f22eb568be60
     * @param receiverId
     */
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}
