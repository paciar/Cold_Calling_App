package com.example.coldcallingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mDate, mStudentName;
    private ImageView mStudentImg;
    private Button mRandomizeButton, mCalledLogButton, mUncalledLogButton;

    private String[] studentNames = {"Aamir Ali", "Dhruv Amin", "Alexander Aney",
            "Daniel Belkin", "Eli Bui", "Noam Canter", "Kenny Cao", "Gareth Chaston",
            "Matthew Chen", "Shuyue Chen", "Bipra Dey", "Darren Dong", "Daniel Dultsin",
            "Xinrui Ge", "Jerry He", "Mohammed Ihtisham", "Fardin Iqbal", "Samuel Iskhakov",
            "Sean Kerrigan", "Eden Kogan", "Selina Li", "Ming Lin", "Sebastian Marinescu",
            "Zhian Maysoon", "Ralf Pacia", "Evelyn Paskhaver", "Vasu Patel", "Tejas Ravi",
            "Marc Rosenberg", "Tanushri Sundaram", "Dennis Wang", "Michael Wu", "Adrian Yan"};

    private int[] studentImages = {R.drawable.aamir_ali, R.drawable.dhruv_amin, R.drawable.alexander_aney,
            R.drawable.elie_belkin, R.drawable.eli_bui, R.drawable.noam_canter, R.drawable.kenny_cao,
            R.drawable.gareth_chaston, R.drawable.matthew_chen, R.drawable.shuyue_chen, R.drawable.bipra_dey,
            R.drawable.darren_dong, R.drawable.daniel_dultsin, R.drawable.xinrui_ge, R.drawable.jerry_he,
            R.drawable.mohammed_ihtisham, R.drawable.fardin_iqbal, R.drawable.samuel_iskhakov, R.drawable.sean_kerrigan,
            R.drawable.eden_kogan, R.drawable.selina_li, R.drawable.ming_lin, R.drawable.sebastian_marinescu,
            R.drawable.zhian_maysoon, R.drawable.ralf_pacia, R.drawable.evelyn_paskhaver, R.drawable.vasu_patel,
            R.drawable.tejas_ravi, R.drawable.marc_rosenberg, R.drawable.tanushri_sundaram, R.drawable.dennis_wang,
            R.drawable.michael_wu, R.drawable.adrian_yan};

    private ArrayList<Student> mStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerOnButton();
    }

    public void addListenerOnButton() {
        mDate = (TextView) findViewById(R.id.date);
        mStudentName = (TextView) findViewById(R.id.student_name);
        mStudentImg = (ImageView) findViewById(R.id.student_image);
        mStudents = generateStudents();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");
        Date time = new Date(System.currentTimeMillis());
        mDate.setText(sdf.format(time));
        mDate.setTextSize(22);
        mDate.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        /*
            mRandomizeButton calls callRandom() to display a randomly called student's name and image.
            If "callable" students exist, callRandom() will be called. Otherwise, it notifies the user
            that no more students can be called.
         */
        mRandomizeButton = (Button) findViewById(R.id.randomize_button);
        mRandomizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCallable(mStudents))
                    callRandom();
                else
                    Toast.makeText(MainActivity.this, "No more students can be called.", Toast.LENGTH_SHORT).show();
            }
        });

        /*
            mCalledLogButton calls getCalledStudents() to pass an ArrayList of Students who have
            already been called into CalledLogActivity. It then also starts CalledLogActivity to
            display a ListView of called students, along with how many times they have been called
            and the last time at which they were called.
         */
        mCalledLogButton = (Button) findViewById(R.id.calledlog_button);
        mCalledLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> calledStudentsList = getCalledStudents(mStudents);
                Intent i = new Intent(MainActivity.this, CalledLogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("CALLEDLOG", calledStudentsList);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        /*
            mUncalledLogButton calls getUncalledStudents() to pass an ArrayList of Students who have
            not been called into UncalledLogActivity. It then also starts UncalledLogActivity to
            display a ListView of uncalled students.
         */
        mUncalledLogButton = (Button) findViewById(R.id.uncalledlog_button);
        mUncalledLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> uncalledStudentsList = getUncalledStudents(mStudents);
                Intent i = new Intent(MainActivity.this, UncalledLogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("UNCALLEDLOG", uncalledStudentsList);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

    }

    /*
        generateStudents() is used to instantiate and fill an ArrayList of student objects using
        the studentName and studentImages arrays.
     */
    public ArrayList<Student> generateStudents() {
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < 33; i++) {
            students.add(new Student(studentNames[i], studentImages[i], 0, 0, 0));
        }
        return students;
    }

    /*
        If there are no more "callable" students (see checkCallable() method), then the system will
        display a toast to notify the user that there are no more students that can be called.
     */
    public void callRandom() {
        int rand = (int) (Math.random()*33);
        Student selectedStudent = mStudents.get(rand);

        /*
            If the student has been called less than twice, the program will choose them, display
            their name, image, and increase their call counter. It will also set the last time that
            they were called to the current time.
         */
        if (selectedStudent.getTimesCalled() < 2) {
            selectedStudent.setLastCalled(System.currentTimeMillis());
            mStudentName.setText(selectedStudent.getStudentName());
            mStudentName.setTextSize(22);
            mStudentName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            mStudentImg.setImageResource(selectedStudent.getStudentImg());
            selectedStudent.setTimesCalled(selectedStudent.getTimesCalled()+1); // may not be working
            selectedStudent.setTotalCalled(selectedStudent.getTotalCalled()+1);
        }

        /*
            If the student has been called twice already, the program checks to see whether or not
            40 minutes have passed since the last time the student was called. If it has been
            40 minutes or greater, then the student is called and their call counter is set back to
            1. It will also set the last time that they were called to the current time.
         */
        else { // if (selectedStudent.getTimesCalled() == 2)
            if (System.currentTimeMillis()-selectedStudent.getLastCalled() >= 2400000) {
                selectedStudent.setLastCalled(System.currentTimeMillis());
                mStudentName.setText(selectedStudent.getStudentName());
                mStudentName.setTextSize(22);
                mStudentName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                mStudentImg.setImageResource(selectedStudent.getStudentImg());
                selectedStudent.setTimesCalled(1);
                selectedStudent.setTotalCalled(selectedStudent.getTotalCalled()+1);
            }

            else {
                callRandom();
            }

        }

    }

    /*
        getCalledStudents() checks an ArrayList of Student objects to see if they have been called
        already in the last 40 minutes. It will then add the student's name, total number of times
        called, and the last time at which they were called on into another ArrayList filled with
        other called students and return it.
     */
    public ArrayList<String> getCalledStudents(ArrayList<Student> studentArrayList) {
        ArrayList<String> calledStudentList = new ArrayList<>();
        for (int i=0; i < studentArrayList.size(); i++) {
            if (studentArrayList.get(i).getTimesCalled() > 0)
                calledStudentList.add(studentArrayList.get(i).getStudentName() + "  /  Times called: " +
                        studentArrayList.get(i).getTotalCalled() + "  /  Last called on: " +
                        studentArrayList.get(i).getLastCalledToString());
        }
        return calledStudentList;
    }

    /*
        getUncalledStudents checks an ArrayList of Student objects to see if they have not been called
        in the last 40 minutes. It will then add the students name into another ArrayList filled with
        other uncalled students and return it.
     */
    public ArrayList<String> getUncalledStudents(ArrayList<Student> studentArrayList) {
        ArrayList<String> uncalledStudentList = new ArrayList<>();
        for (int i=0; i < studentArrayList.size(); i++) {
            if (studentArrayList.get(i).getTimesCalled() == 0)
                uncalledStudentList.add(studentArrayList.get(i).getStudentName());
        }
        return uncalledStudentList;
    }

    /*
        checkCallable() is used to iterate through an ArrayList of Student objects and check to see
        whether or not there are "available" students that can be called, meaning that they haven't
        been called on twice yet in the past 40 minutes. callRandom() is already used to check if
        students can be called in regards to time because it resets their mTimesCalled variable to 1
        after 40 minutes.
     */
    public boolean checkCallable (ArrayList<Student> students) {
        for (int i=0; i<students.size(); i++) {
            if (students.get(i).getTimesCalled() < 2)
                return true;
        }
        return false;
    }

}