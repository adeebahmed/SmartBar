package com.cs442.team2.smartbar;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.cs442.team2.smartbar.fragments.OnClickOpenModule;

/**
 * Created by SumedhaGupta on 10/27/16.
 */

public class UserJournalActivity extends FragmentActivity implements OnClickOpenModule {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        UserJournalFragment fragment = new UserJournalFragment();
        fragment.setOpenModuleInterface(this);
        fragmentTransaction.add(R.id.fragment_conatiner, fragment);
        fragmentTransaction.commit();


    }

    @Override
    public void callOpenModule(String module) {

        switch (module) {
            case "UserJournalActivity":
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CalendarFragment fragment = new CalendarFragment();
                UserJournalFragment f =new UserJournalFragment();
                if(f != null)
                    getFragmentManager().beginTransaction().remove(f);
                  //  getSupportFragmentManager().beginTransaction().remove(f).commit();
                /*fragmentTransaction.add(R.id.fragment_conatiner, fragment);
                fragmentTransaction.commit();
                break;*/
        }


    }
}




