package com.example.android.whitecaps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by User on 08-05-2017.
 */
//DisplayMngr to show different screens
public class DisplayMngr {

            DigitalAttendanceMgr digitalAttendanceMgr;
            public DisplayMngr(DigitalAttendanceMgr digitalAttendanceMgr)
            {
                this.digitalAttendanceMgr = digitalAttendanceMgr;
            }
            //show LoginScreen
            public void showLoginScreen(Context context, String mystr)
            {
               context.startActivity(new Intent(context,LoginScreen.class).putExtra("mystr",mystr));
                ((Activity)(context)).finish();

            }

            //show StudentDashboard
            public  void showStudentDasbord(Context context,Student student)
            {
                Intent i = new Intent(context,Student_Dashboard.class);
                //session.createUserLoginSession("User Session ", student.getEmailID());
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
               // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

              context.startActivity(i.putExtra("m",student));
               ((Activity)(context)).finish();
            }

            //show TeacherDashboard
            public  void showTeacherDashboard(Context context ,Teacher teacher)
            {
             context.startActivity(new Intent(context,Teacher_Dashboard.class).putExtra("m",teacher));
                ((Activity)(context)).finish();
            }

             //show AdminDashboard
            public  void showAdminDashboard(Context context ,Admin admin)
            {
              context.startActivity(new Intent(context , Admin_Dashboard.class).putExtra("m",admin));
                //((Activity)(context)).finish();
            }

            //show TakeAttendance Page
            public void takeAttendance(Context context,Teacher teacher, double latitude , double longitutde)
            { Intent intent = new Intent(context,TakeAttendance.class);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitutde);
                intent.putExtra("teacher",teacher);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                ((Activity)(context)).finish();

                //finish();
                //context.startActivity(new Intent(context,TakeAttendance.class).putExtra("teacher",teacher));
            }


    //show OTP Page
            public void showOTP(String Otp , Context context ,String period, String section,String semester, String stream,
                                Teacher teacher , double latitude,double longitutde)
            {   Intent intent = new Intent(context , OTPPage.class);
                intent.putExtra("OTP",Otp);
                intent.putExtra("Period",period);
                intent.putExtra("teacher",teacher);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitutde);
                intent.putExtra("section",section);
                intent.putExtra("semester",semester);
                intent.putExtra("stream",stream);
                context.startActivity(intent);
                ((Activity)(context)).finish();
               // context.startActivity(new Intent(context , OTPPage.class).putExtra("OTP",Otp ,"Period", period));
            }

            //show Upload Database Page
             public  void showUploadDatabase(Context context )
             {
                 context.startActivity(new Intent(context,Manage_Database.class));
                 //((Activity)(context)).finish();
             }

            //show Choose Stream Page
            public  void showChooseStream(Context context )
            {
                context.startActivity(new Intent(context,ChooseStream.class));
                //((Activity)(context)).finish();
            }

    public void giveAttendance(Context context, Student student , double latitude , double longitude)
    {  Intent intent = new Intent(context,GiveAttendance.class);
        intent.putExtra("latitude",latitude);
        intent.putExtra("longitude",longitude);
        intent.putExtra("student",student);
        context.startActivity(intent);
        ((Activity)(context)).finish();
        //context.startActivity(new Intent(context,GiveAttendance.class).putExtra("student",student));
    }
}
