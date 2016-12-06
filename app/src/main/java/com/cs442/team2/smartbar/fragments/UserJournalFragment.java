package com.cs442.team2.smartbar.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TwoLineListItem;

import com.cs442.team2.smartbar.ExpandableListAdapter;
import com.cs442.team2.smartbar.R;
import com.cs442.team2.smartbar.UserEntity;
import com.cs442.team2.smartbar.WorkoutEntity;
import com.cs442.team2.smartbar.WorkoutListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by SumedhaGupta on 11/6/16.
 */

public class UserJournalFragment extends Fragment implements SearchView.OnQueryTextListener {


    ExpandableListView workoutHistory;
    ArrayAdapter workoutListAdapter;
    ArrayList<WorkoutEntity> workoutHistoryDetails;
    OnClickOpenModule onClickOpenModule;
    private DatabaseReference mDatabase;
    SearchView searchView;
     ExpandableListAdapter expListAdapter;

    ArrayList<String> groupList;
    LinkedHashMap exerciseCollection;


    public void setOpenModuleInterface(OnClickOpenModule onClickOpenModule) {
        this.onClickOpenModule = onClickOpenModule;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (TextUtils.isEmpty(newText)) {
           workoutHistory.clearTextFilter();
        } else {
            workoutHistory.setFilterText(newText.toString());
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_user_journal, container, false);
        Bundle bundle = getArguments();
        UserEntity user = (UserEntity) bundle.getSerializable("user");
        groupList = new ArrayList<String>();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //mDatabase.child("users").child(user.getUsername()).setValue(user);

        workoutHistoryDetails = new ArrayList<>();
        exerciseCollection = new LinkedHashMap<String, List<String>>();

        loadWorkouts(user);

        FloatingActionButton cal = (FloatingActionButton) view.findViewById(R.id.calButton);

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOpenModule.callOpenModule("UserJournalActivity", null);
            }
        });


        workoutHistory = (ExpandableListView) view.findViewById(R.id.history);
        searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setQueryHint("Enter exercise Name");
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);

        workoutHistory.setTextFilterEnabled(true);



       /* LinkedHashMap laptopCollection = new LinkedHashMap<String, List<String>>();
        ArrayList<String> a = new ArrayList<>();
        a.add("somethig");
        a.add("asdasdad");
        laptopCollection.put("group1",a);*/
        expListAdapter = new ExpandableListAdapter(
                getActivity(), groupList, exerciseCollection);
        workoutHistory.setAdapter(expListAdapter);


       // workoutListAdapter = new WorkoutListAdapter(getContext(), R.layout.workout_list_txtview, workoutHistoryDetails);
        // Creating the list adapter and populating the list
        /*workoutListAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_2, workoutHistoryDetails) {
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
        };*/
        //workoutHistory.setAdapter(workoutListAdapter);
        //workoutListAdapter.notifyDataSetChanged();

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
                    groupList.add(workout.getExercise().toString());
                    ArrayList<String> aa = new ArrayList<String>();
                    aa.add("Date : "+workout.getDate());
                    aa.add("Bar Weight : "+workout.getBarWeight()+ "lb");
                    exerciseCollection.put(workout.getExercise().toString(),aa);

                }
                  expListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("team2", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
