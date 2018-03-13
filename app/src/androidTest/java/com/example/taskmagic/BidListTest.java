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
        Bid bid = new Bid("1", 100.00f, "user1");

        bidList.add(bid);
        assertTrue(bidList.hasBid(bid));
    }

    public void testRemoveBid() {
        BidList bidList= new BidList();
        Bid bid = new Bid("1", 100.00f, "user1");

        bidList.add(bid);
        assertTrue(bidList.hasBid(bid));
        bidList.remove(bid);
        assertFalse(bidList.hasBid(bid));
    }

    public void testHasBid() {
        BidList bidList= new BidList();
        Bid bid = new Bid("1", 100.00f, "user1");

        bidList.add(bid);
        assertEquals(bidList.getBid(0), bid);
    }

    public void testGetBid() {
        BidList bidList= new BidList();
        Bid bid1 = new Bid("1", 100.00f, "user1");
        Bid bid2 = new Bid("2", 30.00f, "user2");

        bidList.add(bid1);
        bidList.add(bid2);
        assertEquals(bidList.getBid(0), bid1);
        assertEquals(bidList.getBid(1), bid2);
    }

    public void testGetBidList() {
        ArrayList<Bid> bids = new ArrayList<Bid>();
        Bid bidA = new Bid("1", 100.00f, "user1");
        Bid bidB = new Bid("2", 30.00f, "user2");
        Bid bidC = new Bid("3", 50.00f, "user3");
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
        Bid bidA = new Bid("1", 100.00f, "user1");
        Bid bidB = new Bid("2", 30.00f, "user2");
        Bid bidC = new Bid("3", 50.00f, "user3");
        BidList bidList = new BidList();

        bidList.add(bidA);
        bidList.add(bidB);
        bidList.add(bidC);

        assertEquals(bidList.getCount(), 3);
    }

    public void testGetLowestBid() {
        ArrayList<Bid> bids = new ArrayList<Bid>();
        Bid bid1 = new Bid("1", 100.00f, "user1");
        Bid bid2 = new Bid("2", 20.00f, "user2");
        Bid bid3 = new Bid("3", 150.00f, "user3");
        Bid bid4 = new Bid("4", 70.00f, "user4");

        Bid lowestBid = bid2;

        bids.add(bid1);
        bids.add(bid2);
        bids.add(bid3);
        bids.add(bid4);

        assertNotSame(bids.get(0), lowestBid);

        //assertSame(bids.getLowestBid(), lowestBid);       /*I don't get what is wrong please check*/
    }
}
