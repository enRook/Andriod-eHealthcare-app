package com.sicTLC.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.sicTLC.myapplication.UserDBHelper;



public class LoginActivity extends AppCompatActivity {
    // Define and initialize your variables:

    UserDBHelper userDBHelper = new UserDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView signUpText = findViewById(R.id.textView4);
        signUpText.setOnClickListener(view -> {
            Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(signUpIntent);



        });
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> loginUser());

    }


    private void loginUser() {

        EditText usernameEditText = findViewById(R.id.editTextText_Username);
        EditText passwordEditText = findViewById(R.id.editTextTextPassword);
        // Get username and password from EditText fields
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Query the database to retrieve user's role based on username and password
        String userRole = userDBHelper.getUserRole(username, password); // Replace with your actual database query

        // Redirect the user based on their role
        if (userRole != null) {
            if (userRole.equals("Admin")) {
                // Redirect to AdminActivity
                Intent adminIntent = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(adminIntent);
            } else if (userRole.equals("Doctor")) {
                // Redirect to DoctorActivity
                Intent doctorIntent = new Intent(LoginActivity.this, DoctorActivity.class);
                startActivity(doctorIntent);
            } else if (userRole.equals("Patient")) {
                // Redirect to PatientActivity
                Intent patientIntent = new Intent(LoginActivity.this, PatientActivity.class);
                startActivity(patientIntent);
            } else {
                // Handle other roles or invalid roles here
                // Display an error message or handle the scenario as needed
                Toast.makeText(getApplicationContext(), "Invalid user role", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Handle scenario where login credentials are incorrect or user doesn't exist
            // Show an error message or perform appropriate actions
            Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
        }
    }

    // Other methods, variables, and imports...

}
