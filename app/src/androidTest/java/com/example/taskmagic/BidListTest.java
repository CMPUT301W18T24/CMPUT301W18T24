package com.example.taskmagic;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by Yipu on 22/02/2018.
 */

public class BidListTest extends ActivityInstrumentationTestCase2 {
    public BidListTest() {
        super(MainActivity.class);
    }

    public void testAddBid() {
        BidList bidList= new BidList();
        Bid bid = new Bid("123ab", 100.00f, "user1", "John Doe", "Jane Doe");

        bidList.add(bid);
        assertTrue(bidList.hasBid(bid));
    }

    public void testRemoveBid() {
        BidList bidList= new BidList();
        Bid bid = new Bid("123ab", 100.00f, "user1", "John Doe", "Jane Doe");

        bidList.add(bid);
        assertTrue(bidList.hasBid(bid));
        bidList.remove(bid);
        assertFalse(bidList.hasBid(bid));
    }

    public void testHasBid() {
        BidList bidList= new BidList();
        Bid bid = new Bid("123ab", 100.00f, "user1", "John Doe", "Jane Doe");

        bidList.add(bid);
        assertEquals(bidList.getBid(0), bid);
    }

    public void testGetBid() {
        BidList bidList= new BidList();
        Bid bid1 = new Bid("123ab", 100.00f, "user1", "John Doe", "Jane Doe");
        Bid bid2 = new Bid("123ab", 100.00f, "user2", "Jane Doe", "John Doe");

        bidList.add(bid1);
        bidList.add(bid2);
        assertEquals(bidList.getBid(0), bid1);
        assertEquals(bidList.getBid(1), bid2);
    }

    public void testGetBidList() {
        ArrayList<Bid> bids = new ArrayList<Bid>();
        Bid bidA = new Bid("123ab", 100.00f, "user1", "John Doe", "Jane Doe");
        Bid bidB = new Bid("123ab", 100.00f, "user2", "Jane Doe", "Jesse Doe");
        Bid bidC = new Bid("123ab", 100.00f, "user3", "Jesse Doe", "John Doe");
        BidList bidList = new BidList();

        bidList.add(bidA);
        bids.add(bidA);
        bidList.add(bidB);
        bids.add(bidB);
        bidList.add(bidC);
        bids.add(bidC);

        assertEquals(bidList.getBidList(), bids);
    }

    public void testGetCount() {
        Bid bidA = new Bid("123ab", 100.00f, "user1", "John Doe", "Jane Doe");
        Bid bidB = new Bid("123ab", 100.00f, "user2", "Jane Doe", "Jesse Doe");
        Bid bidC = new Bid("123ab", 100.00f, "user3", "Jesse Doe", "John Doe");
        BidList bidList = new BidList();

        bidList.add(bidA);
        bidList.add(bidB);
        bidList.add(bidC);

        assertEquals(bidList.getCount(), 3);
    }
}
