package com.example.taskmagic;

import android.support.annotation.NonNull;

/**
 * Created by Yipu on 22/02/2018.
 */

class Bid{
    private int taskID;
    private String status;      //One of "Processing", "Accepted" or "Declined".
    private float amount;

    public Bid(int taskID, String status, float amount) {
        this.taskID = taskID;
        this.status = status;
        this.amount = amount;
    }

    public Bid(int taskID, float amount) {
        this.taskID = taskID;
        this.status = "Processing";
        this.amount = amount;
    }

    public void setBidStatus(String status){
        this.status = status;
    }

    public float getBidAmount() {
        return this.amount;
    }

}
