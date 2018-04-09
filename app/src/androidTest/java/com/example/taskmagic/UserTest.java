/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.test.ActivityInstrumentationTestCase2;
import com.example.taskmagic.UserProfileFrag;

/**
 * Created by Fanjie on 2018-04-08.
 */

public class UserTest extends ActivityInstrumentationTestCase2{
    public User testUser;
    public UserTest(){
        super(UserProfileFrag.class);
        this.testUser = new User("a", "b", "c", "d", "e", "f");
    }

    public void testFullname(){
        String test = "a";
        String result =testUser.getFullName();

        assertEquals(test, result);
    }

    public void testEmail(){
        String test = "b";
        String result = testUser.getEmailAddress();

        assertEquals(test, result);
    }
    public void testUsername(){
        String test = "c";
        String result = testUser.getUserName();

        assertEquals(test, result);
    }

    public void testPassword(){
        String test = "d";
        String result = testUser.getPassword();

        assertEquals(test, result);
    }

    public void testphoneNumber(){
        String test = "e";
        String result = testUser.getPhoneNumber();

        assertEquals(test, result);
    }
    public void testphoto(){
        String test = "f";
        String result = testUser.getphotoUri();

        assertEquals(test, result);
    }





}
