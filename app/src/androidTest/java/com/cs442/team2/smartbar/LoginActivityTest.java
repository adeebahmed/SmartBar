package com.cs442.team2.smartbar;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * Created by thedeeb on 11/27/16.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void onCreate() throws Exception {

    }

    @Test
    public void successfulLogin(){

    }

    @Test
    public void blankLogin(){

    }

    @Test
    public void nullLogin(){

    }

    @Test
    public void invalidCharLogin(){

    }

}