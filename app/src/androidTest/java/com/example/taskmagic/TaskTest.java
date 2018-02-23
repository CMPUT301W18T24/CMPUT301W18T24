package com.example.taskmagic;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Yipu on 22/02/2018.
 */

public class TaskTest extends ActivityInstrumentationTestCase2 {
    public TaskTest() {
        super(MainActivity.class);
    }

    public void testAddBid() {
        Task task = new Task(1, "Task1", "This is task1.", "Requested");
        Bid bid = new Bid(1, 50.00f);

        task.addBid(bid);

        Bid returnedBid = task.getBids().getBid(0);
        assertEquals(returnedBid, bid);
    }

    public void testGetLowestBid() {
        Task task = new Task(1, "Task1", "This is task1.", "Requested");
        Bid bid = new Bid(1, 50.00f);
        Bid bid2 = new Bid(1, 33.33f);
        Bid bid3 = new Bid(1, 100.00f);
        task.addBid(bid);
        Bid returnedBid = task.getLowestBid();
        assertEquals(returnedBid,bid);

        task.addBid(bid2);
        returnedBid = task.getLowestBid();
        assertEquals(returnedBid,bid2);

        task.addBid(bid3);
        assertEquals(returnedBid,bid2);
    }

}
