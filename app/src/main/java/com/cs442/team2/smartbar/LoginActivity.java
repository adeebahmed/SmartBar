package com.cs442.team2.smartbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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

        Button btnLogIn = (Button) findViewById(R.id.btn_login);
        final Intent profileIntent = new Intent(this, ProfileActivity.class);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(profileIntent);
            }
        });
    }
}
