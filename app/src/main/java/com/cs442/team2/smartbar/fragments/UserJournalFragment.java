package com.cs442.team2.smartbar.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TwoLineListItem;

import com.cs442.team2.smartbar.R;
import com.cs442.team2.smartbar.UserEntity;
import com.cs442.team2.smartbar.WorkoutEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SumedhaGupta on 11/6/16.
 */

public class UserJournalFragment extends Fragment {


    ListView workoutHistory;
    ArrayAdapter workoutListAdapter;
    List<WorkoutEntity> workoutHistoryDetails;
    OnClickOpenModule onClickOpenModule;
    private DatabaseReference mDatabase;

    public void setOpenModuleInterface(OnClickOpenModule onClickOpenModule) {
        this.onClickOpenModule = onClickOpenModule;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_user_journal, container, false);
        Bundle bundle = getArguments();
        UserEntity user = (UserEntity) bundle.getSerializable("user");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //mDatabase.child("users").child(user.getUsername()).setValue(user);

        workoutHistoryDetails = new ArrayList<>();
        loadWorkouts(user);

        Button cal = (Button) view.findViewById(R.id.calButton);

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOpenModule.callOpenModule("UserJournalActivity");
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
                WorkoutEntity data = workoutHistoryDetails.get(position);
                row.getText1().setText(data.getExercise());
                row.getText2().setText(data.getDate());

                return row;
            }
        };
        workoutHistory.setAdapter(workoutListAdapter);
        workoutListAdapter.notifyDataSetChanged();

        return view;
    }

    private void loadWorkouts(UserEntity user) {
        mDatabase.child("users").child(user.getUsername()).child("workouts").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                workoutHistoryDetails.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    WorkoutEntity workout = (WorkoutEntity) snapshot.getValue(WorkoutEntity.class);
                    workoutHistoryDetails.add(workout);
                }
                workoutListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("team2", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
