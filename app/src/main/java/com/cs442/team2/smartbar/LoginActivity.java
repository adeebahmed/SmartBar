package com.cs442.team2.smartbar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs442.team2.smartbar.data.DataBaseHelper;

import java.io.IOException;

public class LoginActivity extends Activity {

    TextView un,pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
/* ADD FOR UPDATING FRIENDS LIST
//    final ArrayList<FrontDetails> resultse = new ArrayList<FrontDetails>();
//    FrontListBaseAdapter asdf = new FrontListBaseAdapter(context, resultse);
//    lv1.setAdapter(new FrontListBaseAdapter(Front.this, resultse));
//    lv1.setOnItemClickListener(new OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> arg0, View arg1,
//        int position, long arg3) {
//            Object o = lv1.getItemAtPosition(position);
//            FrontDetails obj_itemDetails = (FrontDetails)o;
//            Toast.makeText(context, "You have chosen " + ' ' + obj_itemDetails.getMsgType(), Toast.LENGTH_LONG).show();
//        }
//    });
//    */

            Button btnLogIn = (Button) findViewById(R.id.btn_login);
            un = (TextView) findViewById(R.id.input_email);
            pw = (TextView) findViewById(R.id.input_password);

            final Intent profileIntent = new Intent(this, ProfileActivity.class);

            btnLogIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String u = un.getText().toString();
                    String p = pw.getText().toString();
                    Log.d("UN: ", u);
                    Log.d("pw: ", p);
                    if(user_exists(u,p)){
                        profileIntent.putExtra("username", u);
                        startActivity(profileIntent);

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Invalid username or password.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    private boolean user_exists(String u, String p){
        //database racked locally
        DataBaseHelper dbh = new DataBaseHelper(getApplicationContext());

        try {
            dbh.createDataBase();

        } catch (IOException ioe) {
            Log.d("ERROR: ", "Unable to create database");
            //throw new Error("Unable to create database");
        }

        try {
            dbh.openDataBase();

        } catch (Exception sqle) {
            Log.d("ERROR: ", "Unable to create database");
            //throw new Error("Unable to create database | " + sqle.getMessage().toLowerCase());
        }


        String [] credentials = {u,p};
        Cursor cursor = dbh.getReadableDatabase().rawQuery("SELECT * FROM users WHERE email = ? and password = ?;", credentials);
        String result = "";

        while (cursor.moveToNext()) {
            result=cursor.getString(0);
            Log.d("login: ", result);
        }

        cursor.close();
        dbh.close();

        if(!(result.isEmpty())){
            return true;
        }
        else
        {
            return false;
        }
    }
}
