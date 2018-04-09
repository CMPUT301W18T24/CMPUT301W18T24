/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by Fanjie on 2018-04-08.
 */

public class MessageActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public MessageActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        Log.d("SETUP", "setUp()");
    }



    private void login() {
        solo.assertCurrentActivity("Wrong Activity",  MainActivity.class);


        //enter the usrname "dummy3"
        solo.enterText((EditText) solo.getView(R.id.editTextEmail),"12345@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.editTextpassword),"123456");
        solo.clickOnView(solo.getView(R.id.buttonlogin));
        solo.waitForActivity(HomeFeed.class, 200);
        solo.assertCurrentActivity("Wrong Activity", HomeFeed.class);
    }
    public void testMessage(){

        login();
        solo.assertCurrentActivity("Wrong Activity", HomeFeed.class);
        solo.clickOnView(solo.getView(R.id.profile));
        solo.assertCurrentActivity("Wrong Activity", ViewProfileActivity.class);
        solo.waitForActivity(ViewProfileActivity.class, 200);

        solo.clickOnView(solo.getView(R.id.requested_task));
        //solo.clickOnCheckBox(2);

        solo.clickOnText("TaskTest1", 0, true);
        // Search for task Name EditText
        solo.clickOnText("10.00", 0, true);
        solo.clickOnText("fjs");
        solo.clickOnView(solo.getView(R.id.imageButton));

        solo.enterText((EditText) solo.getView(R.id.messageEditText), "hi");
        solo.clickOnView(solo.getView(R.id.sendMessageImagebutton));

        solo.assertCurrentActivity("Wrong Activity", MessageActivity.class);
        solo.goBack();
        solo.goBack();


        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ViewTaskActivity.class);

        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ViewProfileActivity.class);

        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", HomeFeed.class);

        solo.clickOnView(solo.getView(R.id.logout));
    }
}
