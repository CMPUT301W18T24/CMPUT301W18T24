package com.example.taskmagic;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by wuyuandi on 2018/2/21.
 */

public class task extends AppCompatActivity {
    private String id;
    private String description;
    private String status;
    private ArrayList<bid> bids;
    private String statuses;



    Task(String title, String description){
        this.description = description;
        this.status = statuses;
        bids = new ArrayList<bid>();
    }


    public String getId() {
        return id;
    }


    public ArrayList<bid> getBids() {
        return bids;
    }

    public void addBid(bid newBidder){
        bids.add(newBidder);
    }

    public String getDescription(){
        return this.description;
    }
    public void setId(String id) {
        this.id = id;
    }
    public User getRequester(){
        return this.requester;
    }
    public void setDescription(String newDescription){
        this.description = newDescription;
    }
    public void setBidded(){
        this.status = statuses;
    }
    public void setAsigned(){
        this.status = statuses;
    }
    public void setDone(){
        this.status = statuses;
    }
    public String getStatus(){
        return this.status;
    }
    public void setRequester(User newRequester){
        this.requester=newRequester;
    }
    public void setBid(ArrayList<bid> bidders) {
        this.bids = bids;
    }

}


}
