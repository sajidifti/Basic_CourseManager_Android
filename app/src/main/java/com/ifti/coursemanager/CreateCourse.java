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

public class CreateCourse extends AppCompatActivity {

    private EditText courseCodeEditText;
    private EditText courseTitleEditText;
    private EditText capacityEditText;
    private EditText startingDateEditText;
    private EditText mid1DateEditText;
    private EditText mid2DateEditText;
    private EditText finalExamDateEditText;
    private EditText creditsEditText;
    private EditText classScheduleEditText;
    private EditText labScheduleEditText;
    private Button saveButton;
    private Button backButton;

    private CourseDB courseDB; // Database helper instance

    private int courseId = -10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        courseDB = new CourseDB(this); // Initialize the database helper

        courseCodeEditText = findViewById(R.id.courseCodeEditText);
        courseTitleEditText = findViewById(R.id.courseTitleEditText);
        capacityEditText = findViewById(R.id.capacityEditText);
        startingDateEditText = findViewById(R.id.startingDateEditText);
        mid1DateEditText = findViewById(R.id.mid1DateEditText);
        mid2DateEditText = findViewById(R.id.mid2DateEditText);
        finalExamDateEditText = findViewById(R.id.finalExamDateEditText);
        creditsEditText = findViewById(R.id.creditsEditText);
        classScheduleEditText = findViewById(R.id.classScheduleEditText);
        labScheduleEditText = findViewById(R.id.labScheduleEditText);
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);


        Intent intent1 = getIntent();

        if (intent1.hasExtra("COURSE_ID")) {

            courseId = intent1.getIntExtra("COURSE_ID", -1);

            Cursor courseCursor = courseDB.getCourseById(courseId);

            if (courseCursor != null && courseCursor.moveToFirst()) {
                // Use getColumnIndex to get the index of each column in the cursor
                int idIndex = courseCursor.getColumnIndex("ID");
                int courseCodeIndex = courseCursor.getColumnIndex("COURSE_CODE");
                int courseTitleIndex = courseCursor.getColumnIndex("COURSE_TITLE");
                int capacityIndex = courseCursor.getColumnIndex("CAPACITY");
                int startingDateIndex = courseCursor.getColumnIndex("STARTING_DATE");
                int mid1DateIndex = courseCursor.getColumnIndex("MID_1_DATE");
                int mid2DateIndex = courseCursor.getColumnIndex("MID_2_DATE");
                int finalExamDateIndex = courseCursor.getColumnIndex("FINAL_EXAM_DATE");
                int creditsIndex = courseCursor.getColumnIndex("CREDITS");
                int classScheduleIndex = courseCursor.getColumnIndex("CLASS_SCHEDULE");
                int labScheduleIndex = courseCursor.getColumnIndex("LAB_SCHEDULE");
                int instructorIdIndex = courseCursor.getColumnIndex("INSTRUCTOR_ID");

                // Retrieve values from the cursor using the column indices
                String courseId = courseCursor.getString(idIndex);
                String courseCode = courseCursor.getString(courseCodeIndex);
                String courseTitle = courseCursor.getString(courseTitleIndex);
                int capacityInt = courseCursor.getInt(capacityIndex);
                String startingDate = courseCursor.getString(startingDateIndex);
                String mid1Date = courseCursor.getString(mid1DateIndex);
                String mid2Date = courseCursor.getString(mid2DateIndex);
                String finalExamDate = courseCursor.getString(finalExamDateIndex);
                int creditsInt = courseCursor.getInt(creditsIndex);
                String classSchedule = courseCursor.getString(classScheduleIndex);
                String labSchedule = courseCursor.getString(labScheduleIndex);
                int instructorId = courseCursor.getInt(instructorIdIndex);

// Print
                System.out.println("Course ID: " + courseId);
                System.out.println("Course Code: " + courseCode);
                System.out.println("Course Title: " + courseTitle);
                System.out.println("Capacity: " + capacityInt);
                System.out.println("Starting Date: " + startingDate);
                System.out.println("Mid 1 Date: " + mid1Date);
                System.out.println("Mid 2 Date: " + mid2Date);
                System.out.println("Final Exam Date: " + finalExamDate);
                System.out.println("Credits: " + creditsInt);
                System.out.println("Class Schedule: " + classSchedule);
                System.out.println("Lab Schedule: " + labSchedule);
                System.out.println("Instructor ID: " + instructorId);

                String capacity = String.valueOf(capacityInt);
                String credits = String.valueOf(creditsInt);
//                Set Text
                courseCodeEditText.setText(courseCode);
                courseTitleEditText.setText(courseTitle);
                capacityEditText.setText(capacity);
                startingDateEditText.setText(startingDate);
                mid1DateEditText.setText(mid1Date);
                mid2DateEditText.setText(mid2Date);
                finalExamDateEditText.setText(finalExamDate);
                creditsEditText.setText(credits);
                classScheduleEditText.setText(classSchedule);
                labScheduleEditText.setText(labSchedule);

            }

            if (courseCursor != null && !courseCursor.isClosed()) {
                courseCursor.close();
            }


        }


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve course details from EditText fields
                String courseCode = courseCodeEditText.getText().toString();
                String courseTitle = courseTitleEditText.getText().toString();
                String capacityStr = capacityEditText.getText().toString();
                String startingDate = startingDateEditText.getText().toString();
                String mid1Date = mid1DateEditText.getText().toString();
                String mid2Date = mid2DateEditText.getText().toString();
                String finalExamDate = finalExamDateEditText.getText().toString();
                String creditsStr = creditsEditText.getText().toString();
                String classSchedule = classScheduleEditText.getText().toString();
                String labSchedule = labScheduleEditText.getText().toString();

                // Retrieve the user_type from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                int instructorId = sharedPreferences.getInt("USER_ID", -1);

                Toast.makeText(CreateCourse.this, "ID: " + instructorId, Toast.LENGTH_SHORT).show();

                // Validate that required fields are not empty
                if (courseCode.isEmpty() || courseTitle.isEmpty() || capacityStr.isEmpty()
                        || startingDate.isEmpty() || mid1Date.isEmpty() || mid2Date.isEmpty()
                        || finalExamDate.isEmpty() || creditsStr.isEmpty() || classSchedule.isEmpty()
                        || labSchedule.isEmpty()) {
                    Toast.makeText(CreateCourse.this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Parse numeric fields to integers
                int capacity = Integer.parseInt(capacityStr);
                int credits = Integer.parseInt(creditsStr);

                // Create a new course object with the captured details
                long newRowId = 0;

                if (courseId != -10) {
                    // Insert the new course into the database
                    newRowId = courseDB.insertCourse(courseCode, courseTitle, capacity, startingDate,
                            mid1Date, mid2Date, finalExamDate, credits, classSchedule, labSchedule, instructorId);
                } else {
                    newRowId = courseDB.updateCourse(courseId, courseCode, courseTitle, capacity, startingDate,
                            mid1Date, mid2Date, finalExamDate, credits, classSchedule, labSchedule, instructorId);
                }


                if (newRowId != -1) {
                    // Course insertion successful
                    Toast.makeText(CreateCourse.this, "Course created successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateCourse.this, InstructorActivity.class);
                    startActivity(intent);
                    finish(); // Return to the previous activity
                } else {
                    // Course insertion failed
                    Toast.makeText(CreateCourse.this, "Course creation failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateCourse.this, InstructorActivity.class);
                startActivity(intent);
                finish(); // Return to the previous activity
            }
        });
    }


}
