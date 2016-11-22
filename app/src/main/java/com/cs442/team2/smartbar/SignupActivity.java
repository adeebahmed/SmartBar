package com.cs442.team2.smartbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs442.team2.smartbar.data.DataBaseHelper;

import java.io.IOException;

public class SignupActivity extends AppCompatActivity {
    String firstname = "";
    String lastname= "";
    String email = "";
    String password = "";
    String cpass = "";
    String age = "";
    String height = "";
    String weight = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);
        final TextView txtfirstname = (TextView) findViewById(R.id.txtfname);
        final TextView txtlastname = (TextView) findViewById(R.id.txtlname);
        final TextView txtemail = (TextView) findViewById(R.id.txtEmail);
        final TextView txtpassword = (TextView) findViewById(R.id.txtPass);
        final TextView txtcpass = (TextView) findViewById(R.id.txtConfirmPass);
        final TextView txtage = (TextView) findViewById(R.id.txtDOB);
        final TextView txtheight = (TextView) findViewById(R.id.txtHeight);
        //final TextView txtweight = (TextView) findViewById(R.id.txtWeight);

        final Intent loginIntent = new Intent(this, ProfileActivity.class);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstname = txtfirstname.getText().toString();
                lastname = txtlastname.getText().toString();
                email = txtemail.getText().toString();
                password = txtpassword.getText().toString();
                cpass = txtcpass.getText().toString();
                age = txtage.getText().toString();
                height = txtheight.getText().toString();
                //weight = txtweight.getText().toString();

                if (firstname.equals("") || lastname.equals("") || email.equals("")|| password.equals("")
                        || cpass.equals("") || age.equals("") || height.equals("") || weight.equals("")) {
                    Toast.makeText(getApplicationContext(), "Plese fill in all fields.", Toast.LENGTH_SHORT).show();
                }else if (!password.equals(cpass)){
                    Toast.makeText(getApplicationContext(), "Password do not match.", Toast.LENGTH_SHORT).show();
                }
                else{
                    UserEntity newUser = new UserEntity(firstname,lastname,email, password,
                            age,height,weight);
                    create_user(newUser);
                    loginIntent.putExtra("email", email);
                    startActivity(loginIntent);
                }
            }
        });
    }

    public void create_user(UserEntity u){
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

        String[] userdata = {u.getFirstName(),u.getLastName(),u.getEmail(), u.getPassword(),
                String.valueOf(u.getAge()),String.valueOf(u.getHeight()),String.valueOf(u.getWeight())};
        String query = "INSERT INTO users(firstname,lastname,email,password,age,height,weight) " +
                "VALUES(?,?,?,?,?,?,?)";
        Cursor cursor = dbh.getReadableDatabase().rawQuery(query, userdata);
        cursor.close();
        dbh.close();

    }
}

