package com.example.taskmagic;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by Yipu on 22/02/2018.
 */

class Bid implements Serializable {
    private String taskID;
    private boolean accepted;
    private boolean declined;
    private float amount;
    private String provider;
    private String requestor;
    private String taskTitle;
    private String status;

    /**
     * Constructor of Bid
     * @param taskID
     * @param amount
     * @param provider
     */
    public Bid(String taskID, float amount, String provider) {
        this.taskID = taskID;
        this.amount = amount;
        this.provider = provider;
        this.accepted=false;
        this.declined=false;
        this.status = "Processing";
    }

    /**
     * Returns the Id of the Task that current Bid belongs to
     * @return Id String of Task
     */
    public String getTaskID() {
        return taskID;
    }

    /**
     * Changes the Task that the Bid is associated to
     * @param taskID
     */
    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    /**
     * Returns the amount of the Bid
     * @return String
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Changes the amount of the Bid
     * @param amount
     */
    public void setAmount(float amount) { this.amount = amount; }

    /**
     * returns status: accepted/declined
     * @return Boolean
     */
    public boolean isAccepted() {
        return accepted;
    }

    public boolean isDeclined(){
        return declined;

    }

    /**
     * Takes a String and changes the status of current Bid into the String
     * @param status
     */
    public void setBidStatus(String status) {
        this.status = status;
    }

    /**
     * @see
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Changes status of Bid to be Accepted
     */
    public void acceptBid() {
        this.accepted = true;
        setBidStatus("Accepted");
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    public String getRequestor() {
        return requestor;
    }

    public void setTaskTitle(String title){
        this.taskTitle=title;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns the Id String if the Bidder
     * @return Id String of provider
     */
    public String getProvider() {
        return provider;
    }

    /**
     * Changes the associated provider
     * @param provider
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

 /*   public void setAccepted(boolean accepted) {
        this.accepted = accepted;
        updateStatus();

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setDeclined(boolean declined) {
        this.declined = declined;
    }

    public void setDeclined(boolean declined) {
        this.declined = declined;
        updateStatus();
    } */


/*
    private void updateStatus() {
        if (accepted && !declined) {
            this.status = "Accepted";
        } else if (!accepted && declined) {
            this.status = "Declined";
        } else if (accepted && declined) {
            Log.d("Status Error", "Double status.");
        }
    }*/


}
