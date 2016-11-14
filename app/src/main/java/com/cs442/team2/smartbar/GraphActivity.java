package com.cs442.team2.smartbar;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.cs442.team2.smartbar.data.DataBaseHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;


/**
 * Created by pshanmuganathan on 11/12/2016.
 */

public class GraphActivity extends Activity {
    DataBaseHelper wDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Bundle bundle = getIntent().getExtras();
        int month = bundle.getInt("month");
        month++;
        int day = bundle.getInt("day");
        int tday = day;
        int year = bundle.getInt("year");
        int userid= bundle.getInt("userid");

        double[] x = {0,0,0};
        int i=0;

        wDbHelper = new DataBaseHelper(this);
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
        while(i<3) {
            String sql = "select * from workouts where date like '%"+year+"-"+month+"-"+tday+"%' and fk_user_id="+userid;
            Cursor cursor = wDbHelper.getReadableDatabase().rawQuery(sql, null);
            while (cursor.moveToNext())

            {

                int sets = cursor.getInt(cursor.getColumnIndex("sets"));
                int reps = cursor.getInt(cursor.getColumnIndex("reps"));
                int weight = cursor.getInt(cursor.getColumnIndex("weight"));
                double temp = ((double)weight * (double)sets * (double)reps) / (double)1000;
                x[i] = temp;

            }
            tday++;
            i++;
        }
        String date1 = Integer.toString(month)+"/"+Integer.toString(day)+"/"+Integer.toString(year);
        String date2 = Integer.toString(month)+"/"+Integer.toString(day+1)+"/"+Integer.toString(year);
        String date3 = Integer.toString(month)+"/"+Integer.toString(day+2)+"/"+Integer.toString(year);



        GraphView graph = (GraphView) findViewById(R.id.graph);



        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, x[0]),
                new DataPoint(1, x[1]),
                new DataPoint(2, x[2])
        });

        graph.addSeries(series);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {date1,date2,date3});


        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space




        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(1);
        graph.getViewport().setMaxY(5);
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.BLUE);


        graph.getGridLabelRenderer().setHumanRounding(false);
    }
}
