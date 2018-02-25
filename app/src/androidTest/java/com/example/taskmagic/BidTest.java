package com.example.taskmagic;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Yipu on 22/02/2018.
 */

public class BidTest extends ActivityInstrumentationTestCase2 {
    public BidTest() {
        super(MainActivity.class);
    }

<<<<<<< HEAD
    public void testAcceptBid() {
        int testTaskID = 123;
        float testAmount = 456.00f;
        String testProvider = "Provider Test";

        Bid newBid = new Bid(testTaskID, testAmount, testProvider);
        assertEquals(newBid.getStatus(), "Processing");

        newBid.acceptBid();
        assertEquals(newBid.getStatus(), "Accepted");
    }

    public void testDeclineBid() {
        int testTaskID = 123;
        float testAmount = 456.00f;
        String testProvider = "Provider Test";

        Bid newBid = new Bid(testTaskID, testAmount, testProvider);
        assertEquals(newBid.getStatus(), "Processing");
=======
    public void testSetStatus() {
        Bid bid = new Bid(1, 50.00f, "user");

        assertEquals(bid.getStatus(), "Processing");
        bid.setBidStatus("Accepted");
        assertEquals(bid.getStatus(), "Accepted");
        bid.setBidStatus("Declined");
        assertEquals(bid.getStatus(), "Declined");
>>>>>>> 58fb82380764ecaecb33c25e019da951ab9e0d29

        newBid.acceptBid();
        assertEquals(newBid.getStatus(), "Declined");
    }

}
