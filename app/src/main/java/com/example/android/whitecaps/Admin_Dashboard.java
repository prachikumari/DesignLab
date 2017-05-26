package com.example.android.whitecaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//Admin Dashboard is an activity class where admin clicks
// on upload database,manage database and query database
//to perform respective actions
public class Admin_Dashboard extends AppCompatActivity {

             private static TextView username;
             private static Button uploadBtn,manageBtn;
             Admin admin;
             DigitalAttendanceMgr dam = new DigitalAttendanceMgr();

             @Override
             protected void onCreate(Bundle savedInstanceState) {

                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_admin__dashboard);
                Intent intent = getIntent();
                admin=intent.getParcelableExtra("m");
                username=(TextView)findViewById(R.id.textView2);
                username.setText(admin.getName());
                uploadBtn =(Button)findViewById(R.id.upload);
                manageBtn = (Button)findViewById(R.id.manage);

                buttonFunction();
                manageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(Admin_Dashboard.this,UploadDatabase.class);
                        startActivity(intent1);
                    }
                });

            }

            public void buttonFunction()
            {
                uploadBtn.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dam.dm.showUploadDatabase(Admin_Dashboard.this);
                            }
                        }
                );
            }
}
