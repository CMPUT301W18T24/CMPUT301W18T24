/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Fanjie on 2018-04-08.
 */

public class ChatMessageTest extends ActivityInstrumentationTestCase2 {
    public ChatMessage chatMessage;

    public ChatMessageTest(){
        super(ChatMessage.class);
        this.chatMessage = new ChatMessage("hello","12345", "54321","2012-12-12","tester1","tester2");
    }

    public void testMessage(){
        String test = "hello";
        String result = chatMessage.getMessage();
        assertEquals(test, result);
    }
    public void testSenderId(){
        String test = "12345";
        String result = chatMessage.getSenderId();
        assertEquals(test, result);
    }

    public void testReceiverId(){
        String test = "54321";
        String result = chatMessage.getReceiverId();
        assertEquals(test, result);
    }
    public void testdate(){
        String test = "2012-12-12";
        String result = chatMessage.getDate();
        assertEquals(test, result);
    }
    public void testReceiverName(){
        String test = "tester1";
        String result = chatMessage.getReceiverName();
        assertEquals(test, result);
    }
    public void testSenderName(){
        String test = "tester2";
        String result = chatMessage.getSenderName();
        assertEquals(test, result);
    }
}
