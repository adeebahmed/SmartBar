package com.cs442.team2.smartbar;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.cs442.team2.smartbar.fragments.CalendarFragment;
import com.cs442.team2.smartbar.fragments.OnClickOpenModule;
import com.cs442.team2.smartbar.fragments.UserJournalFragment;

/**
 * Created by SumedhaGupta on 10/27/16.
 */

public class UserJournalActivity extends FragmentActivity implements OnClickOpenModule {

    UserJournalFragment userJournalFragment;
    UserEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        Intent intent = getIntent();
        if (intent != null) {
            user = (UserEntity) intent.getSerializableExtra("user");
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        userJournalFragment = new UserJournalFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        userJournalFragment.setArguments(bundle);
        userJournalFragment.setOpenModuleInterface(this);
        fragmentTransaction.replace(R.id.fragment_conatiner, userJournalFragment, "user_journal");
        fragmentTransaction.commit();
    }

    @Override
    public void callOpenModule(String module) {

        switch (module) {
            case "UserJournalActivity":
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CalendarFragment fragment = new CalendarFragment();
                fragmentTransaction.replace(R.id.fragment_conatiner, fragment, "calendar");
                fragmentTransaction.addToBackStack("user_journal");
                fragmentTransaction.commit();
                break;
        }


    }
}




