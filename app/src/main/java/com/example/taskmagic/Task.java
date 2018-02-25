package com.example.taskmagic;

import java.util.ArrayList;

/**
 * Created by Yipu on 21/02/2018.
 */

class Task {
    private int id;
    private String title;
    private String description;
    private String requester;
    private String status = "Requested";
    private ArrayList<Photo> photos = new ArrayList<Photo>();
    private BidList bids = new BidList();

    public Task(int id, String title, String description, String requester) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Task(int id, String title, String description, String requester, String status, ArrayList<Photo> photos) {
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
        return bids.getCount();
    }

    public int getID() {
        return id;
    }

    public BidList getBids() {
        return bids;
    }

    public Bid getLowestBid() {
        return bids.getLowestBid();
    }

    public String getStatus() {
        return status;
    }

    public void acceptBid(Bid bid) {
        if (bids.hasBid(bid)) {
            this.setStatus("Assigned");
            bid.setBidStatus("Accepted");
        }
    }

    public void declineBid(Bid bid) {
        if (bids.hasBid(bid)) {
            bid.setBidStatus("Declined");
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }
}
