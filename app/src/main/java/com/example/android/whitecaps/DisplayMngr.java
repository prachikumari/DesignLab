package com.example.android.whitecaps;

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
            }

            //show StudentDashboard
            public  void showStudentDasbord(Context context,Student student)
            {
                Intent i = new Intent(context,Student_Dashboard.class);
                SessionManager session = new SessionManager(context);
                session.createUserLoginSession("User Session ", student.getEmailID());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

              context.startActivity(i.putExtra("m",student));
                finish();
            }

            //show TeacherDashboard
            public  void showTeacherDashboard(Context context ,Teacher teacher)
            {
             context.startActivity(new Intent(context,Teacher_Dashboard.class).putExtra("m",teacher));
            }

             //show AdminDashboard
            public  void showAdminDashboard(Context context ,Admin admin)
            {
              context.startActivity(new Intent(context , Admin_Dashboard.class).putExtra("m",admin));
            }

            //show TakeAttendance Page
            public void takeAttendance(Context context,Teacher teacher, double latitude , double longitutde)
            { Intent intent = new Intent(context,TakeAttendance.class);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitutde);
                intent.putExtra("teacher",teacher);
                context.startActivity(intent);

                //finish();
                //context.startActivity(new Intent(context,TakeAttendance.class).putExtra("teacher",teacher));
            }



    private void finish() {
    //super.finish();
    }

    //show OTP Page
            public void showOTP(String Otp , Context context ,String period,Teacher teacher , double latitude,double longitutde)
            {   Intent intent = new Intent(context , OTPPage.class);
                intent.putExtra("OTP",Otp);
                intent.putExtra("Period",period);
                intent.putExtra("teacher",teacher);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitutde);
                context.startActivity(intent);
               // context.startActivity(new Intent(context , OTPPage.class).putExtra("OTP",Otp ,"Period", period));
            }

            //show Upload Database Page
             public  void showUploadDatabase(Context context )
             {
                 context.startActivity(new Intent(context,Manage_Database.class));
             }

            //show Choose Stream Page
            public  void showChooseStream(Context context )
            {
                context.startActivity(new Intent(context,ChooseStream.class));
            }

    public void giveAttendance(Context context, Student student , double latitude , double longitude)
    {  Intent intent = new Intent(context,GiveAttendance.class);
        intent.putExtra("latitude",latitude);
        intent.putExtra("longitude",longitude);
        intent.putExtra("student",student);
        context.startActivity(intent);
        //context.startActivity(new Intent(context,GiveAttendance.class).putExtra("student",student));
    }
}
