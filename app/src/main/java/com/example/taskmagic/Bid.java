package com.example.taskmagic;

import android.support.annotation.NonNull;

/**
 * Created by Yipu on 22/02/2018.
 */

class Bid{
    private int taskID;
    private String status;      //One of "Processing", "Accepted" or "Declined".
    private float amount;
    private String provider;

    public Bid(int taskID, float amount, String provider, String status) {
        this.taskID = taskID;
        this.status = status;
        this.amount = amount;
        this.provider = provider;
    }

    public Bid(int taskID, float amount,String provider) {
        this.taskID = taskID;
        this.status = "Processing";
        this.amount = amount;
        this.provider = provider;
    }

    public void setBidStatus(String status){
        this.status = status;
    }

    public float getBidAmount() {
        return this.amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
