package com.example.taskmagic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Yipu on 22/02/2018.
 */

class BidList {
    private ArrayList<Bid> bids = new ArrayList<Bid>();

    /**
     * Takes a Bid and adds it to the list
     * @param bid
     */
    public void add(Bid bid) {
        bids.add(bid);
    }

    /**
     * Takes a Bid and removes it from the List
     * @param bid
     */
    public void remove(Bid bid) {
        bids.remove(bid);
    }

    /**
     * Takes a Bid and checks the List if identical Bid is in the List
     * @param bid
     * @return Boolean
     */
    public Boolean hasBid(Bid bid) {
        return bids.contains(bid);
    }

    /**
     * Returns the size of the list
     * @return
     */
    public int getCount() {
        return bids.size();
    }

    /**
     * returns the list of Bids
     * @return
     */
    public ArrayList<Bid> getBidList() {
        return bids;
    }

    /**
     * Takes an int index and returns the Bid at position index of BidList
     * @param index
     * @return
     */
    public Bid getBid(int index) {
        return bids.get(index);
    }
}
