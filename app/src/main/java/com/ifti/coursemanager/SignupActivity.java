package com.ifti.coursemanager;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText fullNameEditText, newUsernameEditText, emailEditText, universityEditText, passwordEditText;
    private RadioGroup userTypeRadioGroup;
    private Button signUpSubmitButton;

    // SQLite Database
    private CourseDB courseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize UI elements
        fullNameEditText = findViewById(R.id.fullNameEditText);
        newUsernameEditText = findViewById(R.id.newUsernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        universityEditText = findViewById(R.id.universityEditText);
        userTypeRadioGroup = findViewById(R.id.userTypeRadioGroup);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpSubmitButton = findViewById(R.id.signUpSubmitButton);

//        DB Object
        courseDB = new CourseDB(this);

        // Set click listener for the Sign Up button
        signUpSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle user registration here
                String fullName = fullNameEditText.getText().toString();
                String newUsername = newUsernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String university = universityEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Get the selected user type
                int selectedRadioButtonId = userTypeRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                String userType = selectedRadioButton.getText().toString();

                // Check if required fields are not empty
                if (fullName.isEmpty() || newUsername.isEmpty() || email.isEmpty() || university.isEmpty() || selectedRadioButtonId == -1) {
                    Toast.makeText(SignupActivity.this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
                } else {
                    // Save user registration data to the database
                    saveUserData(fullName, newUsername, email, university, userType, password);
                }
            }
        });
    }

    private void saveUserData(String fullName, String newUsername, String email, String university, String userType, String password) {
        // Insert user data into the 'users' table


        long newRowId = courseDB.insertUser(fullName, newUsername, email, university, userType, password);


        if (newRowId != -1) {
            // Registration successful
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the sign-up activity
        } else {
            // Registration failed
            Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database when the activity is destroyed
        if (courseDB != null) {
            courseDB.close();
        }
    }
}

