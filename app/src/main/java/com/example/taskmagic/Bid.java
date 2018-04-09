package com.example.taskmagic;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by Yipu on 22/02/2018.
 */

class Bid implements Serializable, Comparable<Bid> {
    private String taskID;
    private boolean accepted;
    private boolean declined;
    private float amount;
    private String provider;
    private String requestor;
    private String taskTitle;
    private String providerName;
    private String requestorName;
    private String status;

    public Bid(){}

    /**
     * Constructor of Bid
     * @param taskID
     * @param amount
     * @param provider
     */
    public Bid(String taskID, float amount, String provider, String requestorName) {
        this.taskID = taskID;
        this.amount = amount;
        this.provider = provider;
        this.requestorName = requestorName;
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
     * Returns the Id String if the Bidder
     * @return Id String of provider
     */
    public String getProvider() {
        return provider;
    }

    /**
     * Returns the amount of the Bid
     * @return String
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Changes the associated provider
     * @param provider
     */
    public void setProvider(String provider) {
        this.provider = provider;
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

    public String getRequestor() {
        return requestor;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }


    public String getTaskTitle() {
        return taskTitle;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setTaskTitle(String title){
        this.taskTitle=title;
    }

    @Override
    public int compareTo(@NonNull Bid b2) {
        return (int)(this.amount - b2.getAmount());
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getRequestorName() {
        return requestorName;
    }

    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setDeclined(boolean declined) {
        this.declined = declined;
    }
}
