package com.example.taskmagic;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Yipu on 22/02/2018.
 */

public class BidTest extends ActivityInstrumentationTestCase2 {
    public BidTest() {
        super(MainActivity.class);
    }

    public void testSetStatus() {
        Bid bid = new Bid(1, 50.00f, "user");

        assertEquals(bid.getStatus(), "Processing");
        bid.setBidStatus("Accepted");
        assertEquals(bid.getStatus(), "Accepted");
        bid.setBidStatus("Declined");
        assertEquals(bid.getStatus(), "Declined");

    }

}
