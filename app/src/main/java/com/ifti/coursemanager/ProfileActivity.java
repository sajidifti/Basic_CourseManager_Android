package com.ifti.coursemanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText fullNameEditText;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText universityEditText;
    private Button saveButton;
    int userId = -10;

    private CourseDB courseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fullNameEditText = findViewById(R.id.fullNameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        universityEditText = findViewById(R.id.universityEditText);
        saveButton = findViewById(R.id.saveButton);

        courseDB = new CourseDB(this);

        // Retrieve user profile data and set it to the EditText fields
        retrieveUserProfile();

        // Set click listener for the "Save" button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update user profile data in the database
                updateProfileData();
            }
        });
    }

    private void retrieveUserProfile() {
        // Retrieve user profile data from SharedPreferences or your database
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("USER_ID", 0);
        Cursor cursor = courseDB.selectUserById(userId);


        if (cursor != null && cursor.moveToFirst()) {
            int fullNameInd = cursor.getColumnIndex("FULL_NAME");
            int usernameInd = cursor.getColumnIndex("USERNAME");
            int emailInd = cursor.getColumnIndex("EMAIL");
            int universityInd = cursor.getColumnIndex("UNIVERSITY");


            String fullName = cursor.getString(fullNameInd);
            String username = cursor.getString(usernameInd);
            String email = cursor.getString(emailInd);
            String university = cursor.getString(universityInd);

            // Set the retrieved data to the EditText fields
            fullNameEditText.setText(fullName);
            usernameEditText.setText(username);
            emailEditText.setText(email);
            universityEditText.setText(university);
        }

        // Close the cursor
        if (cursor != null) {
            cursor.close();
        }
    }

    private void updateProfileData() {
        // Retrieve the edited data from EditText fields
        String fullName = fullNameEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String university = universityEditText.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String userType = sharedPreferences.getString("USER_TYPE", null);

        long newRowId = 0;

        if (userId != -10) {
            // Insert the new course into the database
            newRowId = courseDB.updateUser(userId, fullName, username, email, university, userType);
        }

        if (newRowId != -1) {
            // Course insertion successful
            Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

        } else {
            // Course insertion failed
            Toast.makeText(ProfileActivity.this, "Profile update failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
