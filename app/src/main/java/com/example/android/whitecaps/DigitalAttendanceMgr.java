package com.example.android.whitecaps;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Date;

/**
 * Created by User on 08-05-2017.
 */

public class DigitalAttendanceMgr {
    //public LoginScreen;
    public  DisplayMngr dm;
    public LoginMgr lm;
    public  AttenMgr attenMgr;


    public  DigitalAttendanceMgr()
    {
        dm = new DisplayMngr(this);
        attenMgr = new AttenMgr(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void takeAttendanceCall(Context context,Teacher teacher, double latitude , double longitutde)
    {

        attenMgr.takeAttendance(context,teacher,latitude,longitutde);
    }
    public void giveAttendanceCall(Context context,Student student)
    {

        attenMgr.giveAttendance(context,student);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getCurrentTime()

    {
        Calendar calender = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        Date now= calender.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        String time1= date.format(now);
        return time1;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getCurrentDate(){
        DateFormat dateFormat2 = new SimpleDateFormat("MM-dd-yyyy");
        String date2 = dateFormat2.format(new Date());
        return date2;
    }
}
