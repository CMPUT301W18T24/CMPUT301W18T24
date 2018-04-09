/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */
package com.example.taskmagic;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.robotium.solo.Solo;


public class SearchActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public SearchActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        Log.d("SETUP", "setUp()");
    }




    private void login() {
        solo.assertCurrentActivity("Wrong Activity",  MainActivity.class);


        //enter the usrname "dummy3"
        solo.enterText((EditText) solo.getView(R.id.editTextEmail),"123@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.editTextpassword),"123456");
        solo.clickOnView(solo.getView(R.id.buttonlogin));
        solo.waitForActivity(HomeFeed.class, 2000);
        solo.assertCurrentActivity("Wrong Activity", HomeFeed.class);
    }

    public void testSearch(){
        login();

        solo.assertCurrentActivity("Wrong Activity", HomeFeed.class);
        solo.clickOnView(solo.getView(R.id.search));
        solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);
        solo.waitForActivity(SearchActivity.class, 2000);
        solo.sleep(2000);

        solo.enterText((EditText) solo.getView(R.id.search_menu), "Task");
        solo.waitForActivity(SearchActivity.class, 2000);
        solo.sleep(2000);
        solo.clickOnText("TaskTest1", 0, true);
        solo.assertCurrentActivity("Wrong Activity", ViewTaskActivity.class);
        solo.waitForActivity(ViewTaskActivity.class, 2000);

        solo.clickOnView(solo.getView(R.id.button_viewTask));


        solo.enterText((EditText) solo.getView(R.id.editText_amount), "10");
        solo.clickOnView(solo.getView(R.id.button_confirm));

        solo.goBack();








    }

}