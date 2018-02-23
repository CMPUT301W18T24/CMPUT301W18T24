package com.example.taskmagic;

/**
 * Created by Yipu on 22/02/2018.
 */

class Bid {
    private int taskID;
    private String status;
    private float amount;

    public Bid(int taskID, String status, float amount) {
        this.taskID = taskID;
        this.status = status;
        this.amount = amount;
    }

    //Changes the status of a given task to accepted
    public void acceptBid(int taskID) {
    }

    //Declines a bid on a given task
    public void declineBide(int taskID) {

    }

}
