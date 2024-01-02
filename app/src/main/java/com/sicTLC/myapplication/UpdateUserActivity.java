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

public class UpdateUserActivity extends AppCompatActivity {
    private UserDBHelper userDBHelper;
    private String selectedBirthDate; // Declare this variable at class level

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        Button birthDateButton = findViewById(R.id.birthDateButton);
        birthDateButton.setOnClickListener(view -> showDatePickerDialog());


        userDBHelper = new UserDBHelper(this); // Initialize the UserDBHelper

        Button updateButton = findViewById(R.id.UpdateButton);
        updateButton.setOnClickListener(view -> updateUser());

        TextView gotoAdminActivityTextView = findViewById(R.id.gotoAdminActivityTextView);
        gotoAdminActivityTextView.setOnClickListener(view -> {
            startActivity(new Intent(UpdateUserActivity.this, AdminActivity.class));
           finish();
        });
    }

    // Rest of your methods...
    // (Including showDatePickerDialog(), calculateAge(), updateUser(), etc.)
    // ... (Assuming those methods are similar to the ones shared earlier)

    // Inside showDatePickerDialog()


    private void updateUser() {
        EditText userIdEditText = findViewById(R.id.editTextText_UserID);
        EditText firstNameEditText = findViewById(R.id.editTextText_FirstName);
        EditText lastNameEditText = findViewById(R.id.editTextText_LastName);
        EditText emailEditText = findViewById(R.id.editTextText_Email);
        EditText passwordEditText = findViewById(R.id.editTextTextGetPassword);
        EditText confirmPasswordEditText = findViewById(R.id.editTextTextConfirmPassword);
        TextView ageTextView = findViewById(R.id.age2);

        Spinner userTypeSpinner = findViewById(R.id.userTypeSpinner);
        String userIdStr = userIdEditText.getText().toString().trim();
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String age = ageTextView.getText().toString().trim();

        String selectedUserType = userTypeSpinner.getSelectedItem().toString();

        if (userIdStr.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()
                || selectedBirthDate.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || age.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            Log.d("UpdateUserActivity", "Validation failed: Please fill in all fields");
            return; // Exit the method if any field is empty
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return; // Exit the method if passwords don't match
        }

        // Update the user
        long userId = Long.parseLong(userIdStr);
        userDBHelper = new UserDBHelper(this);

        if (!userDBHelper.doesUserIdExist(userId)) {
            Toast.makeText(getApplicationContext(), "User ID does not exist", Toast.LENGTH_SHORT).show();
            return; // Exit the method if the user ID doesn't exist
        }

        boolean isUpdated = userDBHelper.updateUser(userId, firstName, lastName, password, email, selectedBirthDate, selectedUserType, age);

        if (isUpdated) {
            Log.d("UpdateUserActivity", "User updated successfully");
            Toast.makeText(getApplicationContext(), "User updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("UpdateUserActivity", "Failed to update user");
            Toast.makeText(getApplicationContext(), "Failed to update user", Toast.LENGTH_SHORT).show();
        }
    }


    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String formattedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                    selectedBirthDate = formattedDate;

                    int age = calculateAge(formattedDate);
                    TextView ageTextView = findViewById(R.id.age2); // Corrected ID
                    ageTextView.setText("Age: " + age);
                },
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
