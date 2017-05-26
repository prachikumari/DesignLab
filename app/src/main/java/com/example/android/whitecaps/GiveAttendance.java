package com.example.android.whitecaps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GiveAttendance extends AppCompatActivity {

    EditText enterotp;
    Button submit;
    Student student;
    String studentId, studname;
    String result;
    double latitude, longitude;
    String time1;
    int length;
    DigitalAttendanceMgr mDigitalAttendanceMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_give_attendance);

        Intent intent = getIntent();
        student = intent.getParcelableExtra("student");
        latitude = intent.getDoubleExtra("latitude", 0.0);
        longitude = intent.getDoubleExtra("longitude", 0.0);

        studentId = student.getStudEnrollID();
        studname = student.getName();
        mDigitalAttendanceMgr = new DigitalAttendanceMgr();
        enterotp = (EditText) findViewById(R.id.otpvalue);
        submit = (Button) findViewById(R.id.submit);
        btnclick();
    }

    private void btnclick() {
        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AttenMgr attenMgr = new AttenMgr(GiveAttendance.this);
                        attenMgr.submit(enterotp.getText().toString(), latitude, longitude, student);

                    }
                });

    }
}