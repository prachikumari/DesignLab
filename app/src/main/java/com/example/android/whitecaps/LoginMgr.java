package com.example.android.whitecaps;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;

import static com.example.android.whitecaps.LoginScreen.showalert;

/**
 * Created by User on 09-05-2017.
 */

public class LoginMgr {
    DataMgr dataMgr;
    private String emailid;
    private String password;
    DigitalAttendanceMgr dm;
    Student student;
    Admin admin;


    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    private String table;
    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    SessionManager session;

    public void login(Context context)
    {
       dataMgr = new DataMgr(context);
        try {
            dataMgr.createDataBase();
        } catch (IOException e) {e.printStackTrace();}

        dataMgr.getWritableDatabase();
        boolean match_user=dataMgr.getUser(getEmailid(),getTable());// vaerify if username is valid
        String pass = dataMgr.searchPass(getEmailid(), table);//getting password corresponding to the valid username


        //Checking all possible conditions and showing alert with suitable message
        if(getEmailid().equals("")|| getPassword().equals("")){
            String msg ="Please enter empty fields";
            showalert(msg,context);
        }
        else if (getPassword().equals(pass)&& match_user) {
            Toast.makeText(context, "User and Password is correct", Toast.LENGTH_SHORT).show();
           // Intent intent = null;
            dm = new DigitalAttendanceMgr();
            if (table == "student") {
               // student = new Student();
                student= dataMgr.readStudent(getEmailid(),getTable(),student);
                dm.dm.showStudentDasbord(context,student);

                //On successful login as student move to student dashboard activity
            } else if (table == "admin") {

               admin= dataMgr.readAdmin(getEmailid(),getTable(),admin);
                dm.dm.showAdminDashboard(context,admin);

            } else if (table == "teacher") {

               Teacher t= dataMgr.readTeacher(getEmailid(),getTable());
                dm.dm.showTeacherDashboard(context,t);

            }

        }
        else if( match_user==false)
        {
            String msg ="Invalid Username";
           showalert(msg,context);
        }
        else if(match_user && password!=pass)
        {
            String msg ="Invalid Password";
            showalert(msg,context);
        }
    }

}


