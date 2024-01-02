package com.sicTLC.myapplication;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.os.Bundle;
import java.util.ArrayList;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.content.Intent;

import android.view.View;
import android.widget.Button;


public class AdminActivity extends AppCompatActivity {

    private TableLayout tableLayout ;
    private UserDBHelper userDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        tableLayout = findViewById(R.id.tableLayout);
        userDBHelper = new UserDBHelper(this);

        displayUserTable();

        Button addAdminDoctorButton = findViewById(R.id.buttonAddAdminDoctor);
        addAdminDoctorButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AddAdminDoctorActivity.class);
                startActivity(intent);
            }
        });
        Button buttonDeleteUser = findViewById(R.id.buttonDeleteUser);
        buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, DeleteUserActivity.class);
                startActivity(intent);
            }
        });

        Button buttonUpdateUser = findViewById(R.id.buttonUpdateUser);
        buttonUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, UpdateUserActivity.class);
                startActivity(intent);
            }
        });

        Button mysteryButton = findViewById(R.id.buttonMystery);

        mysteryButton.setOnClickListener(view -> {
            // Intent to start the BayonettaActivity
            Intent intent = new Intent(AdminActivity.this, BayonettaActivity.class);
            startActivity(intent);
        });

    }


    private void displayUserTable() {
        ArrayList<User> users = userDBHelper.getAllUsers();

        for (User user : users) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            TextView userIdTextView = createTextView(String.valueOf(user.getId()), 10, 0, 10, 0);
            TextView usernameTextView = createTextView(user.getUsername(), 10, 0, 10, 0);
            TextView firstNameTextView = createTextView(user.getFirstName(), 10, 0, 10, 0);
            TextView lastNameTextView = createTextView(user.getLastName(), 10, 0, 10, 0);
            TextView emailTextView = createTextView(user.getEmail(), 10, 0, 10, 0);
            TextView birthdateTextView = createTextView(user.getBirthdate(), 10, 0, 10, 0);
            TextView userRoleTextView = createTextView(user.getUserRole(), 10, 0, 10, 0);
            TextView ageTextView = createTextView(String.valueOf(user.getAge()), 10, 0, 10, 0);

            tableRow.addView(userIdTextView);
            tableRow.addView(usernameTextView);
            tableRow.addView(firstNameTextView);
            tableRow.addView(lastNameTextView);
            tableRow.addView(emailTextView);
            tableRow.addView(birthdateTextView);
            tableRow.addView(userRoleTextView);
            tableRow.addView(ageTextView);

            tableLayout.addView(tableRow);
        }
    }

    private TextView createTextView(String text, int left, int top, int right, int bottom) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(left, top, right, bottom);
        return textView;
    }
}