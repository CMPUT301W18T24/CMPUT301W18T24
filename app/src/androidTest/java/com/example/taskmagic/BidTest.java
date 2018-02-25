package com.example.taskmagic;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Yipu on 22/02/2018.
 */

public class BidTest extends ActivityInstrumentationTestCase2 {
    public BidTest() {
        super(MainActivity.class);
    }

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

        newBid.acceptBid();
        assertEquals(newBid.getStatus(), "Declined");
    }
}
