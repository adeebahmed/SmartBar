package com.cs442.team2.smartbar.fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs442.team2.smartbar.R;
import com.cs442.team2.smartbar.UserEntity;
import com.cs442.team2.smartbar.WorkoutEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Abhishekgupta on 11/23/16.
 */

public class AddNoteFragment extends Fragment {


    private DatabaseReference mDatabase;
    WorkoutEntity workout;
    private Date workoutDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final View v = inflater.inflate(R.layout.fragment_note, container, false);


        SharedPreferences sharedPreferences = getContext().getSharedPreferences("smartbar", MODE_PRIVATE);
        String userString = sharedPreferences.getString("user", "");

        Gson gson = new Gson();
        final UserEntity user = gson.fromJson(userString, UserEntity.class);


        final EditText nodeEdtText = (EditText) v.findViewById(R.id.noteEdtText);
        Button saveNoteBtn = (Button) v.findViewById(R.id.saveBtn);
        Bundle bundle = getArguments();
        if (bundle != null) {
            workout = (WorkoutEntity) bundle.getSerializable("workout");
            workoutDate = (Date) bundle.getSerializable("workoutDate");
            nodeEdtText.setText(workout.getNotes());
        }
        saveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                workout.setNotes(nodeEdtText.getText().toString());
                String workoutId = workout.getwId();
                mDatabase.child("users").child(user.getUsername()).child("workouts").child(workoutId).setValue(workout).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "Added Notes...", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        Button setRemindeerBtn = (Button) v.findViewById(R.id.setReminder);
        setRemindeerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                Date today = cal.getTime();
                //blue color for past workouts
                if (removeTime(workoutDate).compareTo(removeTime(today)) > 0) {


                    Calendar beginTime = Calendar.getInstance();
                    beginTime.setTime(workoutDate);
                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                            .putExtra(CalendarContract.Events.TITLE, workout.getExercise())
                            .putExtra(CalendarContract.Events.DESCRIPTION, workout.getNotes())
                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                    startActivityForResult(intent, 100);
                    getFragmentManager().popBackStack();
                }else{
                    Snackbar.make(v,"This workout has already ended!",Snackbar.LENGTH_SHORT).show();

                }
            }
        });


        return v;
    }


        public Date removeTime(Date date) {
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date);
            cal2.set(Calendar.HOUR_OF_DAY, 0);
            cal2.set(Calendar.MINUTE, 0);
            cal2.set(Calendar.SECOND, 0);
            cal2.set(Calendar.MILLISECOND, 0);
            return cal2.getTime();
        }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd:M:yyyy");
            String date = sdf.format(workoutDate);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext())
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("SmartBar")
                    .setContentText(date + ": Reminder has been set for " + workout.getExercise());

            NotificationManager mNotificationManager =
                    (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
