package com.example.coldcallingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Student {

    /*
        mStudent name stores the student's name.
        mStudentImg stores the drawable file of the student's image.
        mTimesCalled stores the number of the times the student has been called in the last
        40 minutes.
        mTotalCalled stores the total number of times the student has been called since the application
        started.
        mLastCalled stores the time of which the student had been last called.
     */

    private String mStudentName;
    private int mStudentImg, mTimesCalled, mTotalCalled;
    private long mLastCalled;

    public Student(String studentName, int studentImg, int timesCalled, int totalCalled, long lastCalled) {
        mStudentName = studentName;
        mStudentImg = studentImg;
        mTimesCalled = timesCalled;
        mTotalCalled = totalCalled;
        mLastCalled = lastCalled;
    }

    public String getStudentName() { return mStudentName; }
    public int getStudentImg() { return mStudentImg; }
    public int getTimesCalled() { return mTimesCalled; }
    public void setTimesCalled(int num) { mTimesCalled = num; }
    public int getTotalCalled() { return mTotalCalled; }
    public void setTotalCalled(int num) { mTotalCalled = num; }
    public void setLastCalled(long time) { mLastCalled = time; }
    public long getLastCalled() { return mLastCalled; }

    /*
        getLastCalledToString() returns a formatted String object of the last time the student
        was called.
     */
    public String getLastCalledToString() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm aa");
        Date time = new Date(mLastCalled);
        return sdf.format(time);
    }

}
