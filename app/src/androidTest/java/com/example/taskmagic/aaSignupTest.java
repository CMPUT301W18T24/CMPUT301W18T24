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
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by Fanjie on 2018-03-18.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)

public class aaSignupTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void SignupTest() {
        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        onView(withId(R.id.textViewNewUser)).perform(click());

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.editTextUserName)).perform(replaceText("12345678"), closeSoftKeyboard());
        //check if text has been successfully typed in editTextUserName
        allOf(withId(R.id.editTextUserName), withText("123"), isDisplayed());

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        onView(withId(R.id.editTextPass)).perform(replaceText("123456"), closeSoftKeyboard());
        //check if text has been successfully typed in editTextPass
        allOf(withId(R.id.editTextPass), withText("123456"), isDisplayed());

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        onView(withId(R.id.editTextEmail)).perform(replaceText("12345@gmail.com"), closeSoftKeyboard());
        //check if text has been successfully typed in editTextEmial
        allOf(withId(R.id.editTextEmail), withText("1234@gmail.com"), isDisplayed());

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.editTextPhone)).perform(replaceText("1234567890"), closeSoftKeyboard());
        //check if text has been successfully typed in editTextPhone
        allOf(withId(R.id.editTextPhone), withText("1234567890"), isDisplayed());


        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.editTextfName)).perform(replaceText("tester 1"), closeSoftKeyboard());
        //check if text has been successfully typed in editTextfName
        allOf(withId(R.id.editTextfName), withText("tester 1"), isDisplayed());


        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.buttonsave)).perform(click());


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }

}
