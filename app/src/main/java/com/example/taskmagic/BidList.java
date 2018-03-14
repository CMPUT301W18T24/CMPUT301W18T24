package com.example.taskmagic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Yipu on 22/02/2018.
 */

class BidList {
    private ArrayList<Bid> bids = new ArrayList<Bid>();

    public void add(Bid bid) {
        bids.add(bid);
    }

    public void remove(Bid bid) {
        bids.remove(bid);
    }

    public Boolean hasBid(Bid bid) {
        return bids.contains(bid);
    }

    public int getCount() {
        return bids.size();
    }

    public ArrayList<Bid> getBidList() {
        return bids;
    }

    public Bid getBid(int index) {
        return bids.get(index);
    }


}
