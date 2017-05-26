package com.example.android.whitecaps;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//Login As Page
public class mainPage extends AppCompatActivity {
            String mystr;
            Button studentBtn ,teacherBtn, adminBtn;
            DigitalAttendanceMgr dm;
            @Override
            protected void onCreate(Bundle savedInstanceState) {//execution starts here
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main_page);
                LoginAs();//method called

            }
            public  void LoginAs() {

                //only student can login clicking on this button
                studentBtn= (Button)findViewById(R.id.student);
                studentBtn.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mainPage.this,"Success login as Student",
                                        Toast.LENGTH_SHORT).show();
                                int i = 1;//i=1 is set corresponding to student
                                 mystr = i+"";
                                 new DigitalAttendanceMgr().dm.showLoginScreen(mainPage.this , mystr);

                                }
                        }
                );

                //only teacher can login clicking on this button
                teacherBtn= (Button)findViewById(R.id.teacher);
                teacherBtn.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mainPage.this,"Success login as Teacher",
                                        Toast.LENGTH_SHORT).show();
                                int i = 2;//i=2 is set corresponding to teacher
                                mystr = i+"";
                                new DigitalAttendanceMgr().dm.showLoginScreen(mainPage.this , mystr);

                            }
                        }
                );

                //only admin can login clicking on this button
                adminBtn= (Button)findViewById(R.id.admin);
                adminBtn.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mainPage.this,"Success login as Admin",
                                        Toast.LENGTH_SHORT).show();
                                int i = 3;//i=3 is set corresponding to admin
                                 mystr = i+"";
                                new DigitalAttendanceMgr().dm.showLoginScreen(mainPage.this , mystr);
                            }
                        }
                );


            }

}
