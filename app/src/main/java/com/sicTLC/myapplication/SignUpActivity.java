package com.sicTLC.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.os.Bundle;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.util.Log;
import android.content.Intent;

import com.sicTLC.myapplication.UserDBHelper;


public class SignUpActivity extends AppCompatActivity {
    private UserDBHelper userDBHelper;
    private String selectedBirthDate; // Declare this variable at class level
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button birthDateButton = findViewById(R.id.birthDateButton);
        birthDateButton.setOnClickListener(view -> showDatePickerDialog());

        userDBHelper = new UserDBHelper(this); // Initialize the UserDBHelper


        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(view -> validateInputs());

        TextView gotoLoginTextView = findViewById(R.id.gotoLoginTextView);
        gotoLoginTextView.setOnClickListener(view -> {
            // Go back to LoginActivity
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish(); // Optional: Close the SignUpActivity to prevent going back to it with the back button
        });

    }

    private void validateInputs() {
        EditText usernameEditText = findViewById(R.id.editTextText_Username2);
        EditText firstNameEditText = findViewById(R.id.editTextText_FirstName);
        EditText lastNameEditText = findViewById(R.id.editTextText_LastName);
        EditText emailEditText = findViewById(R.id.editTextText_Email);
        EditText passwordEditText = findViewById(R.id.editTextTextGetPassword);
        EditText confirmPasswordEditText = findViewById(R.id.editTextTextConfirmPassword);
        TextView ageTextView = findViewById(R.id.Age);

        Spinner userTypeSpinner = findViewById(R.id.userTypeSpinner);
        Spinner genderSpinner = findViewById(R.id.gender);

        String username = usernameEditText.getText().toString().trim();
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String age = ageTextView.getText().toString().trim();

        String selectedUserType = userTypeSpinner.getSelectedItem().toString();
        String selectedGender = genderSpinner.getSelectedItem().toString();

        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()
                ||selectedBirthDate.isEmpty()||password.isEmpty() || confirmPassword.isEmpty() || age.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {
            // Perform further actions like saving the user to the database
            saveUserToDatabase(username, firstName, lastName, password, email,selectedBirthDate,selectedUserType, age);

        }

    }

    private void saveUserToDatabase(String username, String firstName, String lastName,
                                    String password, String email,String SelectedBirthDate, String SelectedUserType, String age) {


        if (userDBHelper.doesUsernameExist(username)) {
            // Username already exists, display a message asking the user to pick another
            Toast.makeText(getApplicationContext(), "Username already exists. Please choose another username.", Toast.LENGTH_SHORT).show();
        } else {
            // Username is unique, proceed with user registration
            long userId = userDBHelper.insertUser(username, firstName, lastName, password, email, SelectedBirthDate,SelectedUserType, age);

            if (userId != -1) {
                // Insertion successful
                Log.d("SignUpActivity", "User registered successfully with ID: " + userId);
                Toast.makeText(getApplicationContext(), "User registered successfully!", Toast.LENGTH_SHORT).show();
            } else {
                // Insertion failed
                Log.e("SignUpActivity", "Failed to register user");
                Toast.makeText(getApplicationContext(), "Failed to register user", Toast.LENGTH_SHORT).show();
            }
        }
    }




    // Inside showDatePickerDialog()
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String formattedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                    selectedBirthDate = formattedDate; // Store the selected date in a variable
                    int age = calculateAge(formattedDate);

                    TextView ageTextView = findViewById(R.id.Age);
                    ageTextView.setText("Age: " + age);
                },
                // Set default date to 2000-01-01 or any other default date
                2000, 0, 1);
        datePickerDialog.show();
    }


    private int calculateAge(String birthdate) {
        // Get the current date
        Calendar today = Calendar.getInstance();
        int currentYear = today.get(Calendar.YEAR);
        int currentMonth = today.get(Calendar.MONTH) + 1;
        int currentDay = today.get(Calendar.DAY_OF_MONTH);

        // Parse the birthdate
        String[] dateParts = birthdate.split("-");
        int birthYear = Integer.parseInt(dateParts[0]);
        int birthMonth = Integer.parseInt(dateParts[1]);
        int birthDay = Integer.parseInt(dateParts[2]);

        // Calculate age
        int age = currentYear - birthYear;
        if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
            age--;
        }
        return age;
    }



}