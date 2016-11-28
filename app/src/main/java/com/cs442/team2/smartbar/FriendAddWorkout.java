package com.cs442.team2.smartbar;
//
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.support.v4.app.TaskStackBuilder;
//import android.support.v7.app.NotificationCompat;
//import android.util.Log;
//
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.R;
//
//import com.cs442.team2.smartbar.data.DataBaseHelper;
//
//import org.w3c.dom.Text;
//
//import java.io.IOException;
//
//public class FriendAddWorkout extends AppCompatActivity
//{
//    DataBaseHelper wDbHelper;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_addworkout);
//
//        String text = getIntent().getStringExtra("friendName");
//
//        wDbHelper = new DataBaseHelper(this);
//        try {
//
//            wDbHelper.createDataBase();
//
//        } catch (IOException ioe) {
//
//            throw new Error("Unable to create database");
//
//        }
//        try {
//
//            wDbHelper.openDataBase();
//
//        } catch (Exception sqle) {
//
//            //throw sqle;
//
//        }
//
//        String sql = "select * from users where first_name like '%"+text+"%'";
//        Cursor cursor = wDbHelper.getReadableDatabase().rawQuery(sql, null);
//
//        while (cursor.moveToNext())
//
//        {
//
//            String firstname= cursor.getString(cursor.getColumnIndex("first_name"));
//            String lastname= cursor.getString(cursor.getColumnIndex("last_name"));
//            int age = cursor.getInt(cursor.getColumnIndex("age"));
//            final int userid = cursor.getInt(cursor.getColumnIndex("_id"));
//            String location= cursor.getString(cursor.getColumnIndex("Location"));
//            String name = firstname+"\t"+lastname;
//            final int weight = cursor.getInt(cursor.getColumnIndex("weight"));
//            int height = cursor.getInt(cursor.getColumnIndex("height"));
//
//            double x = weight * 0.45;
//            double y = height * 0.025;
//            double z= y * y;
//            double bmi = x / z;
//
//
//            TextView tname = (TextView)findViewById(R.id.text1);
//            TextView tage = (TextView)findViewById(R.id.text2);
//            TextView tlocation = (TextView)findViewById(R.id.text3);
//            TextView tbmi = (TextView)findViewById(R.id.text4);
//
//
//            tname.setText(name);
//            tage.setText(Integer.toString(age));
//            tlocation.setText(location);
//            tbmi.setText(String.format("%.2f",bmi));
//
//            Button wrkbutton = (Button)findViewById(R.id.WorkButton);
//
//            wrkbutton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
//                                    .setSmallIcon(R.drawable.notification_icon)
//                                    .setContentTitle("Friend Workout")
//                                    .setContentText("Friend has requested Workout");
//                    // Creates an explicit intent for an Activity in your app
//                    Intent resultIntent = new Intent(this, CalendarActivity.class);
//
//                    // The stack builder object will contain an artificial back stack for the
//                    // started Activity.
//                    // This ensures that navigating backward from the Activity leads out of
//                    // your application to the Home screen.
//                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//                    // Adds the back stack for the Intent (but not the Intent itself)
//                    stackBuilder.addParentStack(CalendarActivity.class);
//                    // Adds the Intent that starts the Activity to the top of the stack
//                    stackBuilder.addNextIntent(resultIntent);
//                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT
//                            );
//                    mBuilder.setContentIntent(resultPendingIntent);
//                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                    // mId allows you to update the notification later on.
//                    //mNotificationManager.notify(mId, mBuilder.build();
//                }
//            });
//
//        }
//
//    }
//}