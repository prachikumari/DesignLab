package com.example.android.whitecaps;

import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;
//ShowOTP Page
public class OTPPage extends AppCompatActivity {
    private static TextView tv;
    private static TextView showClock;
    private String OTP;
    private  String Period;
    String provider;
    LocationManager locationManager;
    String startTimeOtp;
    String endTimeOtp;
    static String Text;
    LocationListener mlocListener;
    DigitalAttendanceMgr dm;
    double latitude,longitude;
    DigitalAttendanceMgr mDigitalAttendanceMgr;
    private ProgressBar progressBarCircle;
    private TextView textViewTime;

    //    GPSTracker gps;
Teacher teacher;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {//execution starts here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otppage);
        Intent intent = getIntent();
        OTP = intent.getStringExtra("OTP");//OTP retrieved from AttenMgr class
        Period=intent.getStringExtra("Period");//Period retrieved from AttenMgr class
        teacher = intent.getParcelableExtra("teacher");
        latitude=intent.getDoubleExtra("latitude",0.0);
        longitude= intent.getDoubleExtra("longitude",0.0);
        mDigitalAttendanceMgr = new DigitalAttendanceMgr();
        startTimeOtp= mDigitalAttendanceMgr.getCurrentTime();//gets current time when OTP is generated
        startTimer();//Two minute timer starts


    }

    //Two minute Timer starts
    public void startTimer() {
        //showClock = (TextView) findViewById(R.id.textView7);
        tv = (TextView) findViewById(R.id.textView8);
        progressBarCircle = (ProgressBar) findViewById(R.id.progressBarCircle);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        tv.setText(OTP);
        new CountDownTimer(120000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                textViewTime.setText(hmsTimeFormatter(millisUntilFinished));

                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));

                /*showClock.setText("" + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));*/
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onFinish() {
                //showClock.setText("done!");
                //textViewTime.setText("done!");
                // setProgressBarValues();
                endTimeOtp=mDigitalAttendanceMgr.getCurrentTime(); //After end of two minute timer current time is stored
                // locationl();
                String result = OTP+","+latitude+","+longitude+"," +startTimeOtp+","+endTimeOtp+","+Period;
                FileManager frm = new FileManager(OTPPage.this,result);
                TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                final String tmDevice;
                tmDevice = "" + tm.getDeviceId();
                //frm.SaveInInternalCache(tmDevice,teacher.getTeacherCode());
                frm.SaveInInternalCacheStorage(tmDevice);
                //frm.LoadFromInternalCacheStorage();
                dm = new DigitalAttendanceMgr();
                dm.dm.showTeacherDashboard(OTPPage.this,teacher);
            }
        }.start();

    }

    private void setProgressBarValues() {

        progressBarCircle.setMax(120);
        progressBarCircle.setProgress(120);
    }

    private String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;


    }

    @Override
    public void onBackPressed()
    {

    }
}