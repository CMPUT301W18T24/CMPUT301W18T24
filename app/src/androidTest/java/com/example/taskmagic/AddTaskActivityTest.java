package com.example.taskmagic;

import android.app.Activity;
import android.support.test.espresso.action.PressBackAction;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.robotium.solo.Solo;


public class AddTaskActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public AddTaskActivityTest(){
        super(MainActivity.class);
    }

    public void setUp() throws Exception{
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

    public void testCreateTask() {
        login();
        solo.assertCurrentActivity("Wrong Activity", HomeFeed.class);
        solo.clickOnView(solo.getView(R.id.addTask));
        solo.assertCurrentActivity("Wrong Activity", CreateTaskActivity.class);
        solo.waitForActivity(CreateTaskActivity.class, 200);

        // Search for task Name EditText
        EditText task_name_EditText = (EditText) solo.getView(R.id.task_title);
        assertEquals("", task_name_EditText.getText().toString());

        // Search for task Reason EditText
        EditText task_description_EditText = (EditText) solo.getView(R.id.task_description);
        assertEquals("", task_description_EditText.getText().toString());

        // Search for finish Date TextView
        TextView finishDate_TextView = (TextView) solo.getView(R.id.date_field);
        assertEquals("Finish By", finishDate_TextView.getText().toString());

        solo.getCurrentActivity().finish();
        solo.assertCurrentActivity("Wrong Activity", HomeFeed.class);

        solo.clickOnView(solo.getView(R.id.logout));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    public void testCreateTaskIndeed(){
        login();
        solo.assertCurrentActivity("Wrong Activity", HomeFeed.class);
        solo.clickOnView(solo.getView(R.id.addTask));
        solo.assertCurrentActivity("Wrong Activity", CreateTaskActivity.class);
        solo.waitForActivity(CreateTaskActivity.class, 2000);

        solo.enterText((EditText) solo.getView(R.id.task_title), "TaskTest1");
        solo.enterText((EditText) solo.getView(R.id.task_description), "TaskTest1Des");
        // Set start Date
        // Reference: https://stackoverflow.com/questions/6837012/robotium-how-to-set-a-date-in-date-picker-using-robotium
        solo.clickOnText("Finish By");
        solo.setDatePicker(0, 2018, 11-1, 11);
        solo.clickOnText("OK");
        // Click on Monday checkbox

        // click save button
        solo.clickOnView(solo.getView(R.id.post_task_button));

        solo.clickOnText("Ok");

        // Go back to MyHabits Activity
        solo.waitForActivity(HomeFeed.class, 2000);
        solo.assertCurrentActivity("Wrong Activity", HomeFeed.class);

        solo.clickOnView(solo.getView(R.id.logout));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

    }

}