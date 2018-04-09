package com.example.taskmagic;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Chiia on 3/19/2018.
 */

public class UserTaskTest extends ActivityInstrumentationTestCase2 {
    public UserTask userTask;
    public UserTaskTest() {
        super(MainActivity.class);
        this.userTask = new UserTask("task", "task des", "123456", "requestor", "photo", "2018-8-11");
    }
    public void testtitle(){
        String test = "task";
        String result = userTask.getTitle();

        assertEquals(test, result);
    }

    public void testdescription(){
        String test = "task des";
        String result = userTask.getDescription();

        assertEquals(test, result);
    }

    public void testuserId(){
        String test = "-1";
        String result = userTask.getId();

        assertEquals(test, result);
    }

    public void testrequestorName(){
        String test = "requestor";
        String result = userTask.getRequesterName();

        assertEquals(test, result);
    }
    public void testphoto(){
        String test = "photo";
        String result = userTask.getPhotoUriString();

        assertEquals(test, result);
    }

    public void testdate(){
        String test = "2018-8-11";
        String result = userTask.getDate();

        assertEquals(test, result);
    }



}
