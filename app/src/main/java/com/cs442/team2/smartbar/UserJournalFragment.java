package com.cs442.team2.smartbar;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import com.cs442.team2.smartbar.data.DataBaseHelper;
import com.cs442.team2.smartbar.fragments.OnClickOpenModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SumedhaGupta on 11/6/16.
 */

public class UserJournalFragment extends Fragment {


    ListView workoutHistory;
    ArrayAdapter workoutListAdapter;
    DataBaseHelper wDbHelper;
    List<Workout_Entity> workoutHistoryDetails;
    SQLiteDatabase db;
    OnClickOpenModule onClickOpenModule;

    public void setOpenModuleInterface(OnClickOpenModule onClickOpenModule) {
        this.onClickOpenModule = onClickOpenModule;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_user_journal, container, false);


        workoutHistoryDetails = new ArrayList<>();
        //wDbHelper = new SmartBarDbHelper(view.getContext());
        //  db = wDbHelper.getReadableDatabase();
        //wDbHelper = new DataBaseHelper();
        wDbHelper = new DataBaseHelper(getActivity());

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

        Button cal = (Button) view.findViewById(R.id.calButton);

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOpenModule.callOpenModule("UserJournalActivity");
                //Intent intent = new Intent(getActivity(), CalendarActivity.class);
                //startActivity(intent);

            }
        });


        workoutHistory = (ListView) view.findViewById(R.id.history);

        // Creating the list adapter and populating the list
        workoutListAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_2, workoutHistoryDetails) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TwoLineListItem row;
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    row = (TwoLineListItem) inflater.inflate(android.R.layout.simple_list_item_2, null);
                } else {
                    row = (TwoLineListItem) convertView;
                }
                Workout_Entity data = workoutHistoryDetails.get(position);
                row.getText1().setText(data.getExercise());
                row.getText2().setText(data.getDate());

                return row;
            }
        };


        //retrieving the data from database
        String sql = "select * from workouts order by _id";
        Cursor cursor = wDbHelper.getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() == 0)

        {
            TextView tv = (TextView) view.findViewById(R.id.NoHistory);
            tv.setVisibility(View.VISIBLE);
        }

        //Gson gson = new Gson();

        while (cursor.moveToNext())

        {
            Workout_Entity p = new Workout_Entity();
            p.setDate(cursor.getString(cursor.getColumnIndex("_id")));
            p.setDate(cursor.getString(cursor.getColumnIndex("date")));
            p.setStartTime(cursor.getString(cursor.getColumnIndex("start")));
            p.setEndTime(cursor.getString(cursor.getColumnIndex("end")));
            p.setwUserId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("fk_user_id"))));
            p.setExercise(cursor.getString(cursor.getColumnIndex("exercise")));
            p.setExerReps(Integer.parseInt(cursor.getString(cursor.getColumnIndex("reps"))));
            p.setExerSets(Integer.parseInt(cursor.getString(cursor.getColumnIndex("sets"))));
            p.setBarWeight(Integer.parseInt(cursor.getString(cursor.getColumnIndex("weight"))));
            workoutHistoryDetails.add(p);

        }

        workoutHistory.setAdapter(workoutListAdapter);
        workoutListAdapter.notifyDataSetChanged();

        return view;
    }
}
