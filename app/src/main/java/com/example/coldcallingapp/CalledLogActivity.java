package com.example.coldcallingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CalledLogActivity extends AppCompatActivity {

    private ListView mCalledLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_called_log);

        mCalledLog = (ListView) findViewById(R.id.called_log);

        /*
            "CALLEDLOG" is the key used to receive the ArrayList of called students from the
            MainActivity class. An ArrayAdapter is also used to put the ArrayList objects into
            the ListView.
         */
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> calledList = bundle.getStringArrayList("CALLEDLOG");
        ArrayAdapter<String> studentArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, calledList);
        mCalledLog.setAdapter(studentArrayAdapter);
    }
}