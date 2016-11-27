package com.cs442.team2.smartbar;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by thedeeb on 11/27/16.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void successfulLogin(){
        onView(withId(R.id.input_email)).perform(typeText("adeeb"), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(typeText("smartbar"), closeSoftKeyboard());
        onView(withText("Login")).perform(click());
    }

    @Test
    public void invalidLogin(){
        onView(withId(R.id.input_email)).perform(typeText("adeeb"), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(typeText("wrongpassword"), closeSoftKeyboard());
        onView(withText("Login")).perform(click());
    }

    @Test
    public void blankLogin(){
        onView(withId(R.id.input_email)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(typeText(""), closeSoftKeyboard());
        onView(withText("Login")).perform(click());
    }

    @Test
    public void invalidCharLogin() throws InterruptedException{
        String invalidChars[] = {".","#","$","[","]"};

        for(int i = 0; i < invalidChars.length; i++){
            onView(withId(R.id.input_email)).perform(typeText(invalidChars[i]), closeSoftKeyboard());
            onView(withId(R.id.input_password)).perform(typeText(invalidChars[i]), closeSoftKeyboard());
        }

        try{
            onView(withText("Login")).perform(click());
        }catch(Exception e){

        }

    }

}