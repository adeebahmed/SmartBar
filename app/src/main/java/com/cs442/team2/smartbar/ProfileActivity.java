package com.cs442.team2.smartbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs442.team2.smartbar.data.DataBaseHelper;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity
{
    DataBaseHelper wDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d("bye","bye");
        String s= "Prasana";
        wDbHelper = new DataBaseHelper(this);
        try {

            wDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }
        try {

            wDbHelper.openDataBase();

        } catch (Exception sqle) {

            //throw sqle;

        }

        String sql = "select * from users where first_name like '%"+s+"%'";
        Cursor cursor = wDbHelper.getReadableDatabase().rawQuery(sql, null);

        while (cursor.moveToNext())

        {

            String firstname= cursor.getString(cursor.getColumnIndex("first_name"));
            String lastname= cursor.getString(cursor.getColumnIndex("last_name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            final int userid = cursor.getInt(cursor.getColumnIndex("_id"));
            String location= cursor.getString(cursor.getColumnIndex("Location"));
            String name = firstname+"\t"+lastname;
            final int weight = cursor.getInt(cursor.getColumnIndex("weight"));
            int height = cursor.getInt(cursor.getColumnIndex("height"));

            double x = weight * 0.45;
            double y = height * 0.025;
            double z= y * y;
            double bmi = x / z;



            TextView tname = (TextView)findViewById(R.id.text1);
            TextView tage = (TextView)findViewById(R.id.text2);
            TextView tlocation = (TextView)findViewById(R.id.text3);
            TextView tbmi = (TextView)findViewById(R.id.text4);


            tname.setText(name);
            tage.setText(Integer.toString(age));
            tlocation.setText(location);
            tbmi.setText(String.format("%.2f",bmi));

            final Intent logoutIntent = new Intent(this, LoginActivity.class);
            final Intent journalIntent = new Intent(this, UserJournalActivity.class);
            final Intent grpahIntent = new Intent(this, PickdateActivity.class);
            final Intent friendsIntent = new Intent(this, LoginActivity.class);
            Button btnlogout = (Button)findViewById(R.id.Button06);
            Button btnjournal = (Button)findViewById(R.id.journal);
            Button btngraph = (Button)findViewById(R.id.Button02);
            Button btnfriends = (Button)findViewById(R.id.Button03);

            btnlogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(logoutIntent);
                }
            });
            btnjournal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(journalIntent);
                }
            });
            btngraph.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putInt("userid",userid);
                    grpahIntent.putExtras(b);
                    startActivity(grpahIntent);
                }
            });
            btnfriends.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(friendsIntent);
                }
            });
        }

    }
}
