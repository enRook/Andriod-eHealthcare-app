package com.sicTLC.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DeleteUserActivity extends AppCompatActivity {

    private EditText userIdEditText;
    private UserDBHelper userDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        userIdEditText = findViewById(R.id.editTextTextEnterId);
        Button deleteButton = findViewById(R.id.deleteButton);

        userDBHelper = new UserDBHelper(this); // Instantiate UserDBHelper

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });
    }

    private void deleteUser() {
        String userId = userIdEditText.getText().toString().trim();

        // Check if the entered ID is not empty
        if (!userId.isEmpty()) {
            int id = Integer.parseInt(userId);

            // Call the deleteUserById method from UserDBHelper to delete the user
            boolean deleted = userDBHelper.deleteUserById(id);

            if (deleted) {
                Toast.makeText(DeleteUserActivity.this, "User with ID " + id + " deleted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DeleteUserActivity.this, "Failed to delete user with ID " + id, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(DeleteUserActivity.this, "Please enter the ID", Toast.LENGTH_SHORT).show();
        }
    }
}
