package com.example.taskmagic;

/**
 * Created by wuyuandi on 2018/2/21.
 */
public class bid {


    private User RequesterID;
    private double price;


    bid(User bidder,Double price){
        this.bidder=bidder;
        this.price=price;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public int getRequesterID() {
        return RequesterID;
    }

    public void setRequesterID(User RequesterID) {
        this.RequesterID = RequesterID;
    }


}
