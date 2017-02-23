package com.ptu.mata.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;

import com.ptu.mata.R;

public class CalendarActivity extends AppCompatActivity {

    //日历对象
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendarView= (CalendarView) findViewById(R.id.cv1);

    }
}
