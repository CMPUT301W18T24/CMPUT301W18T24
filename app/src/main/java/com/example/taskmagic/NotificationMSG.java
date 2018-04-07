/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import java.io.Serializable;

public class NotificationMSG implements Serializable {
    private String senderId;
    private String message;
    private String receiverId;
    private String taskId;
    private String taskTitle;

    public NotificationMSG(){

    }

    public NotificationMSG(String senderId,String receiverId,String message, String taskid, String taskTitle){
        this.senderId=senderId;
        this.message=message;
        this.receiverId=receiverId;
        this.taskId=taskid;
        this.taskTitle=taskTitle;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskid) {
        this.taskId = taskid;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

}
