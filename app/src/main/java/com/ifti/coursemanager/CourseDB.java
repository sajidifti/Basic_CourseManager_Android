package com.ifti.coursemanager;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CourseDB extends SQLiteOpenHelper {
    private Context context;

    public CourseDB(Context context) {
        super(context, "EventDB.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("DB@OnCreate");
        String UsersSQL = "CREATE TABLE " + "USERS" + "(" + "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, " + "FULL_NAME" + " TEXT NOT NULL, " + "USERNAME" + " TEXT NOT NULL, " + "EMAIL" + " TEXT NOT NULL, " + "UNIVERSITY" + " TEXT NOT NULL, " + "USER_TYPE" + " TEXT NOT NULL, " + "PASSWORD" + " TEXT NOT NULL)";


        String CoursesSQL = "CREATE TABLE " + "COURSES" + "(" + "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, " + "COURSE_CODE" + " TEXT NOT NULL, " + "COURSE_TITLE" + " TEXT NOT NULL, " + "CAPACITY" + " INTEGER, " + "STARTING_DATE" + " TEXT, " + "MID_1_DATE" + " TEXT, " + "MID_2_DATE" + " TEXT, " + "FINAL_EXAM_DATE" + " TEXT, " + "CREDITS" + " INTEGER, " + "CLASS_SCHEDULE" + " TEXT, " + "LAB_SCHEDULE" + " TEXT, " + "INSTRUCTOR_ID" + " INTEGER)";

        // Create Enrollments table SQL statement
        String EnrollmentsSQL = "CREATE TABLE " + "ENROLLMENTS" + "(" + "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, " + "STUDENT_ID" + " INTEGER, " + "COURSE_ID" + " INTEGER)";

        // Execute SQL
        db.execSQL(UsersSQL);
        db.execSQL(CoursesSQL);
        db.execSQL(EnrollmentsSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("Write code to modify database schema here");
        // db.execSQL("ALTER table my_table  ......");
    }

    public long insertUser(String fullName, String username, String email, String university, String userType, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId = -1;

        try {
            ContentValues values = new ContentValues();
            values.put("FULL_NAME", fullName);
            values.put("USERNAME", username);
            values.put("EMAIL", email);
            values.put("UNIVERSITY", university);
            values.put("USER_TYPE", userType);
            values.put("PASSWORD", password);

            newRowId = db.insert("USERS", null, values);
        } finally {
            db.close();
        }

        return newRowId;
    }

    public int updateUser(int userId, String fullName, String username, String email, String university, String userType) {
        SQLiteDatabase db = this.getWritableDatabase();
        int numRowsAffected = 0;

        try {
            ContentValues values = new ContentValues();
            values.put("FULL_NAME", fullName);
            values.put("USERNAME", username);
            values.put("EMAIL", email);
            values.put("UNIVERSITY", university);
            values.put("USER_TYPE", userType);

            // Define the WHERE clause to update the specific user by their ID
            String whereClause = "ID" + " = ?";
            String[] whereArgs = {String.valueOf(userId)};

            // Perform the update and get the number of affected rows
            numRowsAffected = db.update("USERS", values, whereClause, whereArgs);
        } finally {
            db.close();
        }

        return numRowsAffected;
    }

    // Method to insert course data into the "COURSES" table
    public long insertCourse(String courseCode, String courseTitle, int capacity, String startingDate, String mid1Date, String mid2Date, String finalExamDate, int credits, String classSchedule, String labSchedule, int instructorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId = -1;

        try {
            ContentValues values = new ContentValues();
            values.put("COURSE_CODE", courseCode);
            values.put("COURSE_TITLE", courseTitle);
            values.put("CAPACITY", capacity);
            values.put("STARTING_DATE", startingDate);
            values.put("MID_1_DATE", mid1Date);
            values.put("MID_2_DATE", mid2Date);
            values.put("FINAL_EXAM_DATE", finalExamDate);
            values.put("CREDITS", credits);
            values.put("CLASS_SCHEDULE", classSchedule);
            values.put("LAB_SCHEDULE", labSchedule);
            values.put("INSTRUCTOR_ID", instructorId);

            newRowId = db.insert("COURSES", null, values);
        } finally {
            db.close();
        }

        return newRowId;
    }

    // Method to update course data in the "COURSES" table
    public long updateCourse(int courseId, String courseCode, String courseTitle, int capacity, String startingDate, String mid1Date, String mid2Date, String finalExamDate, int credits, String classSchedule, String labSchedule, int instructorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long numRowsAffected = 0;

        try {
            ContentValues values = new ContentValues();
            values.put("COURSE_CODE", courseCode);
            values.put("COURSE_TITLE", courseTitle);
            values.put("CAPACITY", capacity);
            values.put("STARTING_DATE", startingDate);
            values.put("MID_1_DATE", mid1Date);
            values.put("MID_2_DATE", mid2Date);
            values.put("FINAL_EXAM_DATE", finalExamDate);
            values.put("CREDITS", credits);
            values.put("CLASS_SCHEDULE", classSchedule);
            values.put("LAB_SCHEDULE", labSchedule);
            values.put("INSTRUCTOR_ID", instructorId);

            // Define the WHERE clause to update the specific course by its ID
            String whereClause = "ID" + " = ?";
            String[] whereArgs = {String.valueOf(courseId)};

            // Perform the update and get the number of affected rows
            numRowsAffected = db.update("COURSES", values, whereClause, whereArgs);
        } finally {
            db.close();
        }

        return numRowsAffected;
    }

    // Method to delete a course by its ID
    public int deleteCourse(int courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int numRowsDeleted = 0;

        try {
            // Define the WHERE clause to delete the specific course by its ID
            String whereClause = "ID" + " = ?";
            String[] whereArgs = {String.valueOf(courseId)};

            // Perform the deletion and get the number of rows deleted
            numRowsDeleted = db.delete("COURSES", whereClause, whereArgs);
        } finally {
            db.close();
        }

        return numRowsDeleted;
    }

    // Method to enroll a student in a course
    public long enroll(int studentId, int courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId = -1;

        try {
            ContentValues values = new ContentValues();
            values.put("STUDENT_ID", studentId);
            values.put("COURSE_ID", courseId);

            newRowId = db.insert("ENROLLMENTS", null, values);
        } finally {
            db.close();
        }

        return newRowId;
    }

    // Method to unenroll a student from a course
    public int unenroll(int studentId, int courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int numRowsDeleted = 0;

        try {
            // Define the WHERE clause to delete the enrollment record for the specific student and course
            String whereClause = "STUDENT_ID = ? AND COURSE_ID = ?";
            String[] whereArgs = {String.valueOf(studentId), String.valueOf(courseId)};

            // Perform the deletion and get the number of rows deleted
            numRowsDeleted = db.delete("ENROLLMENTS", whereClause, whereArgs);
        } finally {
            db.close();
        }

        return numRowsDeleted;
    }

    public Cursor selectCourses() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Define the columns you want to retrieve (you can select specific columns if needed)
            String[] projection = {"ID", "COURSE_CODE", "COURSE_TITLE", "CAPACITY", "STARTING_DATE", "MID_1_DATE", "MID_2_DATE", "FINAL_EXAM_DATE", "CREDITS", "CLASS_SCHEDULE", "LAB_SCHEDULE", "INSTRUCTOR_ID"};

            // Query the "COURSES" table to retrieve all courses
            cursor = db.query("COURSES", projection, null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cursor;
    }

    public Cursor selectCoursesByInstructorId(int instructorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Define the columns you want to retrieve (you can select specific columns if needed)
            String[] projection = {"ID", "COURSE_CODE", "COURSE_TITLE", "CAPACITY", "STARTING_DATE", "MID_1_DATE", "MID_2_DATE", "FINAL_EXAM_DATE", "CREDITS", "CLASS_SCHEDULE", "LAB_SCHEDULE"};

            // Define the WHERE clause to filter courses by instructor ID
            String selection = "ID = ?";
            String[] selectionArgs = {String.valueOf(instructorId)};

            // Query the "COURSES" table to retrieve courses for the specified instructor
            cursor = db.query("COURSES", projection, selection, selectionArgs, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cursor;
    }


    // Method to select all enrollments from the "ENROLLMENTS" table and return a Cursor
    public Cursor selectEnrollments() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Define the columns you want to retrieve (you can select specific columns if needed)
            String[] projection = {"ID", "STUDENT_ID", "COURSE_ID"};

            // Query the "ENROLLMENTS" table to retrieve all enrollments
            cursor = db.query("ENROLLMENTS", projection, null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cursor;
    }

    // Method to check if a user with the given username and password exists
    public boolean isValidUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean isValid = false;
        int userId = -1;
        String userType = null;

        try {
            // Define the columns you want to retrieve
            String[] projection = {"ID", "USER_TYPE"};

            // Define the WHERE clause to check for the username and password
            String selection = "USERNAME = ? AND PASSWORD = ?";
            String[] selectionArgs = {username, password};

            // Query the "USERS" table to check for a matching user
            cursor = db.query("USERS", projection, selection, selectionArgs, null, null, null);

            // Check if the cursor has at least one row (user exists)
            if (cursor != null && cursor.moveToFirst()) {
                int idColumnIndex = cursor.getColumnIndex("ID");
                int userTypeColumnIndex = cursor.getColumnIndex("USER_TYPE");

                if (idColumnIndex != -1 && userTypeColumnIndex != -1) {
                    userId = cursor.getInt(idColumnIndex);
                    userType = cursor.getString(userTypeColumnIndex);
                    isValid = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }

        // Store the user's ID and USER_TYPE in SharedPreferences if the login is successful
// Declare SharedPreferences
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

// Declare SharedPreferences.Editor
        SharedPreferences.Editor editor = sharedPreferences.edit();

// Store the user's ID and USER_TYPE in SharedPreferences if the login is successful
        if (isValid) {
            editor.putInt("USER_ID", userId);
            editor.putString("USER_TYPE", userType);
            editor.apply();
        }


        return isValid;
    }

    public Cursor runQuery(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    public Cursor getCourseById(int courseId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Define the columns you want to retrieve (you can select specific columns if needed)
            String[] projection = {"ID", "COURSE_CODE", "COURSE_TITLE", "CAPACITY", "STARTING_DATE", "MID_1_DATE", "MID_2_DATE", "FINAL_EXAM_DATE", "CREDITS", "CLASS_SCHEDULE", "LAB_SCHEDULE", "INSTRUCTOR_ID"};

            // Define the WHERE clause to filter the course by its ID
            String selection = "ID = ?";
            String[] selectionArgs = {String.valueOf(courseId)};

            // Query the "COURSES" table to retrieve the course with the specified ID
            cursor = db.query("COURSES", projection, selection, selectionArgs, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cursor;
    }

    public Cursor getEnrolledCoursesByStudentId(int studentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Define the columns you want to retrieve (you can select specific columns if needed)
            String[] projection = {"COURSES.ID", "COURSE_CODE", "COURSE_TITLE", "CAPACITY", "STARTING_DATE", "MID_1_DATE", "MID_2_DATE", "FINAL_EXAM_DATE", "CREDITS", "CLASS_SCHEDULE", "LAB_SCHEDULE", "INSTRUCTOR_ID"};

            // Define the JOIN statement to combine the "COURSES" and "ENROLLMENTS" tables
            String joinQuery = "SELECT * FROM COURSES " +
                    "INNER JOIN ENROLLMENTS ON COURSES.ID = ENROLLMENTS.COURSE_ID " +
                    "WHERE ENROLLMENTS.STUDENT_ID = ?";

            // Define the selection arguments
            String[] selectionArgs = {String.valueOf(studentId)};

            // Query the combined result of the "COURSES" and "ENROLLMENTS" tables to retrieve enrolled courses of the specified student
            cursor = db.rawQuery(joinQuery, selectionArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cursor;
    }

    public Cursor selectUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Define the columns you want to retrieve (you can select specific columns if needed)
            String[] projection = {"ID", "FULL_NAME", "USERNAME", "EMAIL", "UNIVERSITY", "USER_TYPE"};

            // Define the WHERE clause to filter by user ID
            String selection = "ID = ?";
            String[] selectionArgs = {String.valueOf(userId)};

            // Query the "USERS" table to retrieve user information by ID
            cursor = db.query("USERS", projection, selection, selectionArgs, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cursor;
    }


}
