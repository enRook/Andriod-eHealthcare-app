package com.sicTLC.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;


public class DoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        Button logoutButton = findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(view -> {
            // Intent to start the Login Activity
            Intent intent = new Intent(DoctorActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Optional: Finish the current activity if needed
        });

    }
}