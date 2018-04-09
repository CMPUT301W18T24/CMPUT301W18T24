package com.example.taskmagic;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Fanjie on 2018-03-18.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)

public class LoginTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void LoginTest() {
        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        onView(withId(R.id.editTextEmail)).perform(replaceText("123@gmail.com"), closeSoftKeyboard());
        //check if text has been successfully typed in editTextEmial
        allOf(withId(R.id.editTextEmail), withText("123@gmail.com"), isDisplayed());


        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        onView(withId(R.id.editTextpassword)).perform(replaceText("123456"), closeSoftKeyboard());
        //check if text has been successfully typed in editTextpassword
        allOf(withId(R.id.editTextpassword), withText("123456"), isDisplayed());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        onView(withId(R.id.buttonlogin)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.logout)).perform(click());

    }

}

