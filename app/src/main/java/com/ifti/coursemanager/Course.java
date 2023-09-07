package com.ifti.coursemanager;

public class Course {
    int id = 0;
    String courseCode = "";
    String courseTitle = "";
    int capacity = 0;
    String startingDate = "";
    String mid1Date = "";
    String mid2Date = "";
    String finalExamDate = "";
    int credits = 0;
    String classSchedule = "";
    String labSchedule = "";
    int instructorId = 0;

    public Course(int id, String courseCode, String courseTitle, int capacity, String startingDate, String mid1Date, String mid2Date, String finalExamDate, int credits, String classSchedule, String labSchedule, int instructorId) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.capacity = capacity;
        this.startingDate = startingDate;
        this.mid1Date = mid1Date;
        this.mid2Date = mid2Date;
        this.finalExamDate = finalExamDate;
        this.credits = credits;
        this.classSchedule = classSchedule;
        this.labSchedule = labSchedule;
        this.instructorId = instructorId;
    }
}
