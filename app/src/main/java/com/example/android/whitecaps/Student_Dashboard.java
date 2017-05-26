package com.example.android.whitecaps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//Student Dashboard
public class Student_Dashboard extends AppCompatActivity {
    private String usr_name;
    String table_name;
    private static TextView username;
    private static TextView course;
    private static TextView locn;
    private static Button btn;
    DataMgr d;
    private String lat,lon,addr;
    GPSTracker gps;
    //Student student;
    Student student;
    DigitalAttendanceMgr digitalAttendanceMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {//execution starts here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__dashboard);
        btn= (Button)findViewById(R.id.giveatten);

        
        Intent intent = getIntent();
        student = intent.getParcelableExtra("m");
        username=(TextView)findViewById(R.id.textView2);
        course=(TextView)findViewById(R.id.textView3);

        username.setText(student.getName());// name set in textview
        course.setText(student.getStream());
        checkLocation();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        checkLocation();
    }

    private void checkLocation() {
        // Show location button click event
        btn.setOnClickListener(new View.OnClickListener() {

            //@Override
            public void onClick(View arg0) {

                    digitalAttendanceMgr = new DigitalAttendanceMgr();

                    digitalAttendanceMgr.giveAttendanceCall(Student_Dashboard.this,student);


            }
        });
    }

    //Alert with message
    public void showalerttime()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("No class is scheduled for given timimg");
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // return;
            }
        });
        alertDialog.show();
    }

}
