package com.cs442.team2.smartbar;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by SumedhaGupta on 12/6/16.
 */


@RunWith(AndroidJUnit4.class)
public class UserJournalActivityTest {

    @Rule
    public ActivityTestRule<UserJournalActivity> ActivityRule = new ActivityTestRule<>(UserJournalActivity.class);

   /* @Rule
    public ActivityTestRule<UserJournalActivity> ActivityRule1 = new ActivityTestRule<>(UserJournalActivity.class);
*/

    private Context c;

    @Before
    public void setup() {
        c = InstrumentationRegistry.getContext();
    }



    @Test
    public void successfulOpenJournalModule() {

        LoginActivityTest test = new LoginActivityTest();
        test.successfulLogin();
        onView(withText("JOURNAL")).perform(click());
        Assert.assertEquals(true, getCurrentActivity(c).toLowerCase().contains("journal"));
    }

    private String getCurrentActivity(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        Log.d("Activity: ", cn.toString());

        return cn.toShortString();
    }


}

