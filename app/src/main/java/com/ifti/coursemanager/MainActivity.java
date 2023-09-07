package com.ifti.coursemanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button signInButton, signUpButton, exitButton;

    // SQLite Database
    private CourseDB courseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);
        exitButton = findViewById(R.id.exitButton);

        // Initialize the SQLite database
        courseDB = new CourseDB(this);


        // Set click listeners for buttons
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle sign-in logic here
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (courseDB.isValidUser(username, password)) {
                    Toast.makeText(MainActivity.this, "Sign-in successful!", Toast.LENGTH_SHORT).show();
// Retrieve the user_type from SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                    String userType = sharedPreferences.getString("USER_TYPE", null);

                    if (userType != null) {
                        if ("Student".equals(userType)) {
                            Toast.makeText(MainActivity.this, "Signed-in As Student", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                            startActivity(intent);
                        } else if ("Teacher".equals(userType)) {
                            Toast.makeText(MainActivity.this, "Signed-in As Teacher", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, InstructorActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid User Type", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // User type not found in SharedPreferences, handle appropriately
                        Toast.makeText(MainActivity.this, "User Type Not Found", Toast.LENGTH_SHORT).show();
                    }

                    finish();
                } else {
                    // Invalid credentials
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle exit button click to close the app
                finish();
                finishAffinity();
            }
        });
    }

}
