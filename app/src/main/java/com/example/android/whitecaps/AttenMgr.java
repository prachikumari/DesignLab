package com.example.android.whitecaps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.text.ParseException;

/**
 * Created by User on 02-05-2017.
 */
//AttenMgr Page
class AttenMgr {
    DigitalAttendanceMgr digitalAttendanceMgr;
    DataMgr dataMgr ;
    Subject subject;
    Context myContext;
    GPSTracker gps;
    public RoutineMgr getRoutineMgr() {
        return mRoutineMgr;
    }

    public void setRoutineMgr(RoutineMgr routineMgr) {
        mRoutineMgr = routineMgr;
        //myContext=mycontext;
    }

    RoutineMgr mRoutineMgr;

    public  AttenMgr(Context context) {
        myContext = context;
    }
       // myContext = context;
     public  AttenMgr(Context context , RoutineMgr routineMgr)
     {
         myContext = context;
         setRoutineMgr(routineMgr);
     }
     public  AttenMgr(DigitalAttendanceMgr dm)
     {

         digitalAttendanceMgr = dm;

     }
     @RequiresApi(api = Build.VERSION_CODES.N)

     public  boolean getCurrentTimeValue(Context context)
     {
         String time1=digitalAttendanceMgr.getCurrentTime();
         SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
         SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
         dataMgr = new DataMgr(context);
         boolean value1=false;
         try {
             value1= dataMgr.checkTime(time1);//checkTime returns boolean value after comparing current time and period timing
         } catch (ParseException e) {e.printStackTrace();}
         return  value1;
     }
     @RequiresApi(api = Build.VERSION_CODES.N)
     public  void takeAttendance(Context context, Teacher teacher, double latitude, double longitutde)
     {   boolean value = getCurrentTimeValue(context);

       // catch (ParseException e) {e.printStackTrace();}

         if (value)
         {//returns true if current time lies in range of period timing
             digitalAttendanceMgr.dm.takeAttendance(context,teacher,latitude,longitutde);

         }
         else//returns false if current time does not lies in range of period timing
         {
             showalerttime(context);//showing alert
         }
     }


    public static void showalerttime(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
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

    public void showalert(String s)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(myContext).create();
        alertDialog.setTitle("Missing Fields");
        alertDialog.setMessage("Please Enter" + s);
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // return;
            }
        });
        alertDialog.show();
    }

    public  void generateOTP() {
        subject = new Subject();
        boolean value = getRoutineMgr().checkEmptyFields(subject);
        if (value == false) {
            showalert("Missing");
        } else {
            dataMgr = new DataMgr(myContext , this);
            // mRoutineMgr=r;

            dataMgr.showConfirmation(subject, getRoutineMgr());
           // String Otp = getOTP();
            //digitalAttendanceMgr.dm.showOTP(Otp, myContext, getRoutineMgr().getSelectedPeriod(), getRoutineMgr().teacher, getRoutineMgr().getLatitude(),
            // getRoutineMgr().getLongitude());
        }
    }
    public  String getOTP(String classid)
    {
        return dataMgr.getOTP(classid,getRoutineMgr().getSelectedPeriod(),getRoutineMgr().getSelectedDay());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)

    public void giveAttendance(Context context , Student student) {
        gps = new GPSTracker(context);

         int k = checkLocation(context);
        if(k==1) {
            boolean value = getCurrentTimeValue(context);
            if (value)
            {  //returns true if current time lies in range of period timing
                digitalAttendanceMgr.dm.giveAttendance(context, student, gps.getLatitude(), gps.getLongitude());
            }
            else//returns false if current time does not lies in range of period timing
            {
                showalerttime(context);//showing alert
            }
        }}


 public int checkLocation(Context context)
 {

     // Check if GPS enabled
     if (gps.canGetLocation()) {

         double latitude = gps.getLatitude();
         double longitude = gps.getLongitude();
         //addr =gps.getCompleteAddressString(latitude,longitude);
         if(latitude != 0.0 && longitude != 0.0) {
             Toast.makeText(context, "Your Location is - \nLat: " + latitude + "\nLong: " +
                     longitude, Toast.LENGTH_LONG).show();
                     return 1;
        }else{
             Toast.makeText(context, "Try Again", Toast.LENGTH_LONG).show();
         return 0;
         }
     } else {
         // Can't get location.
         // GPS or network is not enabled.
         // Ask user to enable GPS/network in settings.
         gps.showSettingsAlert();
        return 0;
     }
 }

    public void extractOtp(String classid) {
        String Otp =getOTP(classid);
        digitalAttendanceMgr= new DigitalAttendanceMgr();
        digitalAttendanceMgr.dm.showOTP(Otp, myContext, getRoutineMgr().getSelectedPeriod(), getRoutineMgr().teacher, getRoutineMgr().getLatitude(),
                getRoutineMgr().getLongitude());

    }

    public void submit(String enterOtp,double latitude , double longitude,Student student)
    {
        if(enterOtp.length()<4)
        {
                Toast.makeText(myContext,"Check OTP",Toast.LENGTH_LONG).show();
        }
        else
        {
            TelephonyManager tm = (TelephonyManager) myContext.getSystemService(myContext.TELEPHONY_SERVICE);
            //tm.getDeviceId();
            //length = enterotp.getText().length();
            final String tmDevice, tmSerial, androidId;
            tmDevice = "" + tm.getDeviceId();
            //tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + Settings.Secure.getString(myContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            // For getting Unique Android ID
            digitalAttendanceMgr = new DigitalAttendanceMgr();
            String time1  = digitalAttendanceMgr.getCurrentTime();
            String date1 = digitalAttendanceMgr.getCurrentDate();
            String result = date1+","+
                    time1+","+latitude+","+longitude+","
                    +enterOtp+","+student.getStudEnrollID()+ ","+student.getName()+","
                    +student.getSemester()+","+student.getStream()+","+student.getSection()+","
                    +tmDevice+","+androidId;
            //Toast.makeText(GiveAttendance.this,result,Toast.LENGTH_LONG).show();
            FileManager fm = new FileManager(myContext,result);
            fm.SaveInInternalCacheStorage(tmDevice);
            //fm.LoadFromInternalCacheStorage();
            Toast.makeText(myContext,"Attendance Taken Successfully. Thank you!",Toast.LENGTH_LONG).show();
            digitalAttendanceMgr.dm.showStudentDasbord(myContext,student);
        }
    }



}


