package com.example.coldcallingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class UncalledLogActivity extends AppCompatActivity {

    private ListView mUncalledLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uncalled_log);

        mUncalledLog = (ListView) findViewById(R.id.uncalled_log);

        /*
            "UNCALLEDLOG" is the key used to receive the ArrayList of uncalled students from the
            MainActivity class. An ArrayAdapter is also used to put the ArrayList objects into
            the ListView.
         */
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> uncalledList = bundle.getStringArrayList("UNCALLEDLOG");
        ArrayAdapter<String> studentArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, uncalledList);
        mUncalledLog.setAdapter(studentArrayAdapter);
    }
}