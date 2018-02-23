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
        Task task = new Task(1, "Task1", "This is task1.");
        Bid bid = new Bid(1, 50.00f, "user1");

        task.addBid(bid);

        Bid returnedBid = task.getBids().getBid(0);
        assertEquals(returnedBid, bid);
    }

    public void testGetLowestBid() {
        Task task = new Task(1, "Task1", "This is task1.");
        Bid bid = new Bid(1, 100.00f, "user1");
        Bid bid2 = new Bid(2, 30.00f, "user2");
        Bid bid3 = new Bid(3, 50.00f, "user3");

        task.addBid(bid);
        Bid returnedBid = task.getLowestBid();
        assertEquals(returnedBid,bid);

        task.addBid(bid2);
        returnedBid = task.getLowestBid();
        assertEquals(returnedBid,bid2);

        task.addBid(bid3);
        assertEquals(returnedBid,bid2);
    }

    public void testGetBidSize() {
        Task task = new Task(1, "Task1", "This is task1.");
        Bid bid = new Bid(1, 50.00f, "user1");
        Bid bid2 = new Bid(1, 33.33f, "user2");
        Bid bid3 = new Bid(1, 100.00f, "user3");
        task.addBid(bid);
        task.addBid(bid2);
        task.addBid(bid3);

        assertEquals(task.getBidsSize(), 3);
    }

    public void testAcceptBid() {
        Task task = new Task(1,"Task1","This is task1.");
        Bid bid = new Bid(1, 1.00f, "user1");

        task.addBid(bid);
        task.acceptBid(bid);

        assertEquals(task.getStatus(), "Assigned");
        assertEquals(bid.getStatus(), "Accepted");
    }

    public void testDeclineBid() {
        Task task = new Task(1,"Task1","This is task1.");
        Bid bid = new Bid(1, 1.00f, "user1");

        task.addBid(bid);
        task.declineBid(bid);

        assertEquals(task.getStatus(), "Requested");
        assertEquals(bid.getStatus(), "Declined");
    }

}
