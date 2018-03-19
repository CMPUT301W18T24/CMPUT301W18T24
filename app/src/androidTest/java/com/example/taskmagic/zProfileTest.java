package com.example.taskmagic;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.view.Menu.FIRST;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
/**
 * Created by Fanjie on 2018-03-19.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class zProfileTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void zProfileTest() {
        try {
            Thread.sleep(3000);
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
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.profile)).perform(click());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        onView(withId(R.id.editTextUserName)).check(matches(withText("123@gmail.com")));
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        onView(withId(R.id.editTextfullName)).check(matches(withText("fjs")));

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        onView(withId(R.id.editTextEmail)).check(matches(withText("123@gmail.com")));

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        onView(withId(R.id.EditTextpassword)).check(matches(withText("123456")));

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        onView(withId(R.id.EditTextphoneNumber)).check(matches(withText("1234567890")));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}