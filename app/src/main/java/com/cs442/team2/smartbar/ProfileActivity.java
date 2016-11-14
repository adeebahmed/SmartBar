package com.cs442.team2.smartbar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /*added by Sumedha Gupta
        *adding button listeners to navigate to other screens
        * */
        //listener for Journal
        Button userJournalButton= (Button) findViewById(R.id.journal);
        userJournalButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserJournalActivity.class);
                startActivity(intent);
            }
        });


    }
}
