package com.cs442.team2.smartbar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class CalendarActivity extends Activity {

    private CalendarView calendarView;
    private int yr, mon, dy;
    private Calendar selectedDate;
    final int DATE_DIALOG_ID = 0;
    private TextView date;


    private DatePickerDialog.OnDateSetListener dateListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int
                        monthOfYear, int dayOfMonth) {
                    selectedDate = Calendar.getInstance();
                    yr = year;
                    mon = monthOfYear;
                    dy = dayOfMonth;
                    updateDisplay();
                    Toast.makeText(getApplicationContext(), "Date choosen is "+ date.getText(),  Toast.LENGTH_SHORT).show();
                    selectedDate.set(yr, mon, dy);

                    calendarView.setDate(selectedDate.getTimeInMillis());
                }
                /** Updates the date in the TextView */
                private void updateDisplay() {
                    StringBuilder a= new StringBuilder().append(mon + 1).append("/")
                            .append(dy).append("/")
                            .append(yr).append(" ");

                    date.setText(a);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        date= (TextView) findViewById(R.id.numberview);


        Calendar c = Calendar.getInstance();
        yr = c.get(Calendar.YEAR);
        mon = c.get(Calendar.MONTH);
        dy = c.get(Calendar.DAY_OF_MONTH);
        Button datePickerButton = (Button) findViewById(R.id.date_picker_button);
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        /** This integer will uniquely define the dialog to be used for displaying date picker.*/

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
                //new DatePickerDialog(getApplicationContext(), dateListener, yr, mon, dy).show();
            }
        });

        calendarView.setOnDateChangeListener(new OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                Intent intent =new Intent(getApplicationContext(), NotesActivity.class);
                startActivity(intent);
                finish();
                // Toast.makeText(getApplicationContext(), "" + dayOfMonth, 0).show();// TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "Selected date is " + (month + 1) + "-" + dayOfMonth + "-" +
                        year, Toast.LENGTH_SHORT).show();

            }
        });
    }

    /** Create a new dialog for date picker */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, dateListener, yr, mon, dy);
        }
        return null;
    }

}
