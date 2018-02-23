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
    private ArrayList<Photo> photos;
    private ArrayList<Bid> bids;

    public Task(int id, String title, String description, String status, ArrayList<Photo> photos, ArrayList<Bid> bids) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.photos = photos;
        this.bids = bids;
    }

}
