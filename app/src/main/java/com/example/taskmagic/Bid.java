package com.example.taskmagic;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by Yipu on 22/02/2018.
 */

class Bid {
    private String taskID;
    private boolean accepted;
    private boolean declined;
    private double amount;
    private String provider;

    private String status;


    public Bid(String taskID, double amount, String provider) {
        this.taskID = taskID;
        this.amount = amount;
        this.provider = provider;
        this.accepted=false;
        this.declined=false;
        this.status = "Processing";
    }



    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public void setAmount(float amount) { this.amount = amount; }

    public boolean isAccepted() {
        return accepted;
    }
    public boolean isDeclined(){
        return declined;

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

    public String getProvider() {
        return provider;
    }

    public double getAmount() {
        return amount;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    private void updateStatus() {
        if (accepted && !declined) {
            this.status = "Accepted";
        } else if (!accepted && declined) {
            this.status = "Declined";
        } else if (accepted && declined) {
            Log.d("Status Error", "Double status.");
        }
    }

    public void setBidStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void acceptBid() {
        this.accepted = true;
        setBidStatus("Accepted");
    }

}
