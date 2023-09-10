package com.ifti.coursemanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    private ListView coursesListView;

    private Button exitButton;
    private Button profileButton;
    private Button enrollButton;

    private CourseDB courseDB;

    private ArrayList<Course> courses;
    private CourseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        coursesListView = findViewById(R.id.coursesListView);
        exitButton = findViewById(R.id.exitButton);
        profileButton = findViewById(R.id.profileButton);
        enrollButton = findViewById(R.id.enrollButton);

        courses = new ArrayList<>();

        courseDB = new CourseDB(this);

        try {
            loadData(); //shows data in list view from db
        } catch (Exception e) {
            Toast.makeText(this, "Failed to Load Data", Toast.LENGTH_SHORT).show();
        }

        // Set click listener for the "New" button
        enrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this, CourseList.class);
                startActivity(intent);
                finish();
            }
        });

        // Set click listener for the "Exit" button
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                finishAffinity();
            }
        });

        // Set click listener for the "Profile" button
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadData() {
        courses.clear();

        Cursor cursor = courseDB.getEnrolledCoursesByStudentId(getStudentId());

        if (cursor.getCount() == 0) {
            Toast.makeText(StudentActivity.this, "No Data", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {
            int courseId = cursor.getInt(0);
            String courseCode = cursor.getString(1);
            String courseTitle = cursor.getString(2);
            int capacity = cursor.getInt(3);
            String startingDate = cursor.getString(4);
            String mid1Date = cursor.getString(5);
            String mid2Date = cursor.getString(6);
            String finalExamDate = cursor.getString(7);
            int credits = cursor.getInt(8);
            String classSchedule = cursor.getString(9);
            String labSchedule = cursor.getString(10);

            Course course = new Course(courseId, courseCode, courseTitle, capacity, startingDate, mid1Date, mid2Date, finalExamDate, credits, classSchedule, labSchedule, getStudentId());

            courses.add(course);

        }

        adapter = new CourseListAdapter(this, courses);

        coursesListView.setAdapter(adapter);

        coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent in = new Intent(StudentActivity.this, CourseDetails.class);
                in.putExtra("COURSE_ID", courses.get(position).id);
                startActivity(in);
                finish();
            }
        });

        coursesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                String message = "Do you want to unroll course - " + courses.get(position).courseCode + " ?";
                showDialog(message, "Unroll Course", courses.get(position).id);
                return true;
            }
        });
    }

    private int getStudentId() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int instructorId = sharedPreferences.getInt("USER_ID", -1);
        return instructorId;
    }

    private void showDialog(String message, String title, int key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                CourseDB db = new CourseDB(getApplicationContext());
                db.unenroll(getStudentId(), key);
                dialog.cancel();
//                loadData();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }

        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onRestart() {
        super.onRestart();
        //adapter.notifyDataSetChanged();
        loadData();
    }
}

