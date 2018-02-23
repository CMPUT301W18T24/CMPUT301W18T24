package com.example.taskmagic;

import java.util.ArrayList;

/**
 * Created by Yipu on 21/02/2018.
 */

class Task {
    private int id;
    private String title;
    private String description;
    private String status;
    private ArrayList<Photo> photos = new ArrayList<Photo>();
    private ArrayList<Bid> bids = new ArrayList<Bid>();

    public Task(int id, String title, String description, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Task(int id, String title, String description, String status, ArrayList<Photo> photos) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.photos = photos;
    }

    public void addBid(Bid bid) {
        bids.add(bid);
    }

    public int getBidsSize() {
        return bids.size();
    }

    public int getID() {
        return id;
    }

}
