package com.example.android.whitecaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//Admin Dashboard
public class Admin_Dashboard extends AppCompatActivity {

      private static TextView username;
      private static Button uploadBtn;
      Admin admin;
      DigitalAttendanceMgr dam = new DigitalAttendanceMgr();

      @Override
      protected void onCreate(Bundle savedInstanceState) {//execution starts here
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_admin__dashboard);
          Intent intent = getIntent();
          admin=intent.getParcelableExtra("m");
          username=(TextView)findViewById(R.id.textView2);
          username.setText(admin.getName());
          uploadBtn =(Button)findViewById(R.id.upload);
          buttonFunction();

      }
      public void buttonFunction()
      {
          uploadBtn.setOnClickListener(//on clicking login button
                  new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          dam.dm.showUploadDatabase(Admin_Dashboard.this);
                      }
                  }
          );
      }
}
