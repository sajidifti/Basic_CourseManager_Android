package com.ifti.coursemanager;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CourseListAdapter extends ArrayAdapter<Course> {
    private final Context context;
    private final ArrayList<Course> courses;

    public CourseListAdapter(@NonNull Context context, @NonNull ArrayList<Course> courses) {
        super(context, -1, courses);
        this.context = context;
        this.courses = courses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.course_item, parent, false);

        // Find and set the TextViews with course code and title
        TextView courseCodeTextView = rowView.findViewById(R.id.courseCodeTextView);
        TextView courseTitleTextView = rowView.findViewById(R.id.courseTitleTextView);

        courseCodeTextView.setText(courses.get(position).courseCode);
        courseTitleTextView.setText(courses.get(position).courseTitle);

        return rowView;
    }
}
