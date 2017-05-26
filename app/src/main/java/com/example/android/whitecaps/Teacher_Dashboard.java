package com.example.android.whitecaps;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//Teacher Dashboard
public class Teacher_Dashboard extends AppCompatActivity {
    private String usr_name;
    private static TextView username;
    Button takeAttenBtn;
    DataMgr helper;
    Teacher teacher;
    GPSTracker gps;
    DigitalAttendanceMgr digitalAttendanceMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {//execution starts here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__dashboard);
        Intent intent = getIntent();
        teacher = intent.getParcelableExtra("m");
        username =(TextView)findViewById(R.id.textView2);
        username.setText(teacher.getName());// name set in textview
        takeAttendanceButton();//method called
    }

    //Take Attendance Button is clicked and event follows
    public void takeAttendanceButton()
    {
         takeAttenBtn = (Button)findViewById(R.id.upload);
         takeAttenBtn.setOnClickListener(new View.OnClickListener() {
             @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
             public void onClick(View v) {
                 gps = new GPSTracker(Teacher_Dashboard.this);

                 // Check if GPS enabled
                 if (gps.canGetLocation()) {

                     double latitude = gps.getLatitude();
                     double longitude = gps.getLongitude();
                     //addr =gps.getCompleteAddressString(latitude,longitude);

                     Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                     digitalAttendanceMgr = new DigitalAttendanceMgr();
                     digitalAttendanceMgr.takeAttendanceCall(Teacher_Dashboard.this,teacher,latitude,longitude);
                     //Toast.makeText(getApplicationContext(), addr, Toast.LENGTH_LONG).show();
                 } else {
                     // Can't get location.
                     // GPS or network is not enabled.
                     // Ask user to enable GPS/network in settings.
                     gps.showSettingsAlert();
                 }

             }
        });
    }
}


