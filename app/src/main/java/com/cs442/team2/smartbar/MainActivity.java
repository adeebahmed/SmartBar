package com.cs442.team2.smartbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    /* ADD FOR UPDATING FRIENDS LIST
    final ArrayList<FrontDetails> resultse = new ArrayList<FrontDetails>();
    FrontListBaseAdapter asdf = new FrontListBaseAdapter(context, resultse);
    lv1.setAdapter(new FrontListBaseAdapter(Front.this, resultse));


    lv1.setOnItemClickListener(new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1,
        int position, long arg3) {

            Object o = lv1.getItemAtPosition(position);
            FrontDetails obj_itemDetails = (FrontDetails)o;
            Toast.makeText(context, "You have chosen " + ' ' + obj_itemDetails.getMsgType(), Toast.LENGTH_LONG).show();

        }
    });
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.activity_main);
    }
}
