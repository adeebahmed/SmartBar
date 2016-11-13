import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;

import com.cs442.team2.smartbar.FriendList;
import com.cs442.team2.smartbar.data.DataBaseHelper;

import java.io.IOException;

public class List_Main extends Activity
{

    DataBaseHelper wDbHelper;
    ListView list;
    String[] names = new String[10] ;
    Integer[] imageId = new Integer[10];
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.activity_list_main);

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





        String mysql = "select fk_user_id from friends where friend_id = "+count;
        Cursor mycursor = wDbHelper.getReadableDatabase().rawQuery(mysql, null);
        while (mycursor.moveToNext())
        {
            String sql = "select name from users where id=fk_user_id";
            Cursor cursor = wDbHelper.getReadableDatabase().rawQuery(sql, null);
            while (cursor.moveToNext())
            {

                String s= cursor.getString(cursor.getColumnIndex("first_name"));
                //Toast.makeText(List_Main.this,"ID is "+ s,Toast.LENGTH_SHORT).show();
                names[count] = s;
                imageId[count] = R.drawable.img1;
                count++;

            }

        }



        FriendList adapter = new FriendList(List_Main.this, names, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(List_Main.this, "You Clicked at " +names[+ position], Toast.LENGTH_SHORT).show();

            }
        });

    }

}