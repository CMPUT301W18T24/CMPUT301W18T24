/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by Fanjie on 2018-04-08.
 */

public class EditTaskActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public EditTaskActivityTest() {
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
        solo.waitForActivity(HomeFeed.class, 2000);
        solo.assertCurrentActivity("Wrong Activity", HomeFeed.class);
    }

    public void testEditTask(){
        login();
        solo.assertCurrentActivity("Wrong Activity", HomeFeed.class);
        solo.clickOnView(solo.getView(R.id.profile));
        solo.assertCurrentActivity("Wrong Activity", ViewProfileActivity.class);
        solo.waitForActivity(ViewProfileActivity.class, 2000);
        solo.sleep(2000);

        solo.clickOnView(solo.getView(R.id.requested_task));
        solo.clickOnCheckBox(1);;

        solo.clickOnText("TaskTest1");
        solo.clickOnView(solo.getView(R.id.button_viewTask));
        // Search for task Name EditText
        EditText task_name_EditText = (EditText) solo.getView(R.id.editText_titleContent);
        assertEquals("TaskTest1", task_name_EditText.getText().toString());

        // Search for task Reason EditText
        EditText task_description_EditText = (EditText) solo.getView(R.id.editText_descriptionContent);
        assertEquals("TaskTest1Des", task_description_EditText.getText().toString());
        solo.clickOnView(solo.getView(R.id.edit_confirm_button));



        solo.goBack();
        solo.goBack();
        solo.goBack();
        solo.goBack();
        solo.goBack();
        solo.goBack();
        solo.goBack();




    }
}
