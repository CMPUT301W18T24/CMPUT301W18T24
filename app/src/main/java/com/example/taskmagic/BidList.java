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

    public Bid getLowestBid() {
        Collections.sort(bids, new Comparator<Bid>() {
            @Override
            public int compare(Bid b1, Bid b2) {
                if (b1.getBidAmount() < b2.getBidAmount()) {
                    return -1;
                }
                if (b1.getBidAmount() > b2.getBidAmount()) {
                    return 1;
                } else {
                    return 0;
                }

            }
        });

        return this.getBid(0);
    }
}
