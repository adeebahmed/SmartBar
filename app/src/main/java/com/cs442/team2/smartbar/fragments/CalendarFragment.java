package com.cs442.team2.smartbar.fragments;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.cs442.team2.smartbar.R;
import com.cs442.team2.smartbar.Workout_Entity;
import com.cs442.team2.smartbar.data.DataBaseHelper;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CalendarFragment extends Fragment {


    private CalendarView calendarView;
    private int yr, mon, dy;
    private Calendar selectedDate;
    final int DATE_DIALOG_ID = 0;
    private TextView date;
    DataBaseHelper wDbHelper;
    List<Workout_Entity> workoutHistoryDetails;
    SQLiteDatabase db;

    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;



    private DatePickerDialog.OnDateSetListener dateListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int
                        monthOfYear, int dayOfMonth) {
                    selectedDate = Calendar.getInstance();
                    yr = year;
                    mon = monthOfYear;
                    dy = dayOfMonth;
                    updateDisplay();
                    Toast.makeText(getContext(), "Date choosen is " + date.getText(), Toast.LENGTH_SHORT).show();
                    selectedDate.set(yr, mon, dy);
                    Calendar cal = Calendar.getInstance();
                    //calendarView.setDate(selectedDate.getTimeInMillis());
                    cal.set(yr,mon,dy);
                }

                /** Updates the date in the TextView */
                private void updateDisplay() {
                    StringBuilder a = new StringBuilder().append(mon + 1).append("/")
                            .append(dy).append("/")
                            .append(yr).append(" ");

                    date.setText(a);
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        //database
        workoutHistoryDetails = new ArrayList<>();
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
        //retrieving the data from database
        String sql = "select * from workouts order by _id";
        Cursor cursor = wDbHelper.getReadableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext())

        {
            Workout_Entity p = new Workout_Entity();
            p.setwId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))));
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

        // end database
        Button datePickerButton = (Button) v.findViewById(R.id.date_picker_button);

        /** This integer will uniquely define the dialog to be used for displaying date picker.*/

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //getActivity().showDialog(DATE_DIALOG_ID);
                new DatePickerDialog(getContext(), dateListener, yr, mon, dy).show();
                //new DatePickerDialog(getApplicationContext(), dateListener, yr, mon, dy).show();
            }
        });


        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        caldroidFragment = new CaldroidFragment();

        // //////////////////////////////////////////////////////////////////////
        // **** This is to show customized fragment. If you want customized
        // version, uncomment below line ****
//		 caldroidFragment = new CaldroidSampleCustomFragment();

        // Setup arguments

        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            // Uncomment this to customize startDayOfWeek
            // args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
            // CaldroidFragment.TUESDAY); // Tuesday

            // Uncomment this line to use Caldroid in compact mode
            // args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

            // Uncomment this line to use dark theme
//            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

            caldroidFragment.setArguments(args);
        }

        setCustomResourceForDates();

        // Attach to the activity
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(getContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getContext(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    Toast.makeText(getContext(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();
                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

        final TextView textView = (TextView) v.findViewById(R.id.textview);

        final Button customizeButton = (Button) v.findViewById(R.id.customize_button);

        // Customize the calendar
        customizeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (undo) {
                    customizeButton.setText(getString(R.string.customize));
                    textView.setText("");

                    // Reset calendar
                    caldroidFragment.clearDisableDates();
                    caldroidFragment.clearSelectedDates();
                    caldroidFragment.setMinDate(null);
                    caldroidFragment.setMaxDate(null);
                    caldroidFragment.setShowNavigationArrows(true);
                    caldroidFragment.setEnableSwipe(true);
                    caldroidFragment.refreshView();
                    undo = false;
                    return;
                }

                // Else
                undo = true;
                customizeButton.setText(getString(R.string.undo));
                Calendar cal = Calendar.getInstance();

                // Min date is last 7 days
                cal.add(Calendar.DATE, -7);
                Date minDate = cal.getTime();

                // Max date is next 7 days
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 14);
                Date maxDate = cal.getTime();

                // Set selected dates
                // From Date
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 2);
                Date fromDate = cal.getTime();

                // To Date
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 3);
                Date toDate = cal.getTime();

                // Set disabled dates
                ArrayList<Date> disabledDates = new ArrayList<Date>();
                for (int i = 5; i < 8; i++) {
                    cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, i);
                    disabledDates.add(cal.getTime());
                }

                // Customize
                caldroidFragment.setMinDate(minDate);
                caldroidFragment.setMaxDate(maxDate);
                caldroidFragment.setDisableDates(disabledDates);
                caldroidFragment.setSelectedDates(fromDate, toDate);
                caldroidFragment.setShowNavigationArrows(false);
                caldroidFragment.setEnableSwipe(false);

                caldroidFragment.refreshView();

                // Move to date
                // cal = Calendar.getInstance();
                // cal.add(Calendar.MONTH, 12);
                // caldroidFragment.moveToDate(cal.getTime());

                String text = "Today: " + formatter.format(new Date()) + "\n";
                text += "Min Date: " + formatter.format(minDate) + "\n";
                text += "Max Date: " + formatter.format(maxDate) + "\n";
                text += "Select From Date: " + formatter.format(fromDate)
                        + "\n";
                text += "Select To Date: " + formatter.format(toDate) + "\n";
                for (Date date : disabledDates) {
                    text += "Disabled Date: " + formatter.format(date) + "\n";
                }

                textView.setText(text);
            }
        });

        Button showDialogButton = (Button) v.findViewById(R.id.show_dialog_button);

        final Bundle state = savedInstanceState;
        showDialogButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Setup caldroid to use as dialog
                dialogCaldroidFragment = new CaldroidFragment();
                dialogCaldroidFragment.setCaldroidListener(listener);

                // If activity is recovered from rotation
                final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
                if (state != null) {
                    dialogCaldroidFragment.restoreDialogStatesFromKey(
                            getActivity().getSupportFragmentManager(), state,
                            "DIALOG_CALDROID_SAVED_STATE", dialogTag);
                    Bundle args = dialogCaldroidFragment.getArguments();
                    if (args == null) {
                        args = new Bundle();
                        dialogCaldroidFragment.setArguments(args);
                    }
                } else {
                    // Setup arguments
                    Bundle bundle = new Bundle();
                    // Setup dialogTitle
                    dialogCaldroidFragment.setArguments(bundle);
                }

                dialogCaldroidFragment.show(getActivity().getSupportFragmentManager(),
                        dialogTag);
            }
        });

        return v;
    }

    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();
        Date blueDate= cal.getTime();

        //highlighting the dates
        for (Workout_Entity w:workoutHistoryDetails
             ) {
            String dateString= w.getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(dateString);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Date today = cal.getTime();

            if(today.compareTo(convertedDate)<0)
            {
                cal.setTime(convertedDate);
                cal.add(Calendar.DATE,0);
                blueDate = cal.getTime();
            }
        }
        /*cal.add(Calendar.DATE, -7);
        Date blueDate = cal.getTime();
*/
        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        Date greenDate = cal.getTime();

        if (caldroidFragment != null) {
            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
            ColorDrawable green = new ColorDrawable(Color.GREEN);
            caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
            caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
            caldroidFragment.setTextColorForDate(R.color.white, blueDate);
            caldroidFragment.setTextColorForDate(R.color.white, greenDate);
        }
    }


    //@Override
    public void onDateSet(DatePicker view, int year, int
            monthOfYear, int dayOfMonth) {
        selectedDate = Calendar.getInstance();
        yr = year;
        mon = monthOfYear;
        dy = dayOfMonth;
        updateDisplay();
        Toast.makeText(getContext(), "Date choosen is " + date.getText(), Toast.LENGTH_SHORT).show();
        selectedDate.set(yr, mon, dy);

        calendarView.setDate(selectedDate.getTimeInMillis());
    }

    private void updateDisplay() {
        StringBuilder a = new StringBuilder().append(mon + 1).append("/")
                .append(dy).append("/")
                .append(yr).append(" ");

        date.setText(a);
    }
}
