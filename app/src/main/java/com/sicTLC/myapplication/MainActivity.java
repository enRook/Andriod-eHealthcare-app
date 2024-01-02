package com.sicTLC.myapplication;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    UserDBHelper userDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example: Navigating to another activity after a delay (e.g., a login activity)
        new android.os.Handler().postDelayed(
                () -> {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Close this activity from the back stack
                },
                3000 // 3 seconds delay as an example

        );
        // Initialize your UserDBHelper instance
        userDBHelper = new UserDBHelper(this);

        // Call the insertDefaultAdmin() method
        userDBHelper.insertDefaultAdmin();
    }

}
