package com.example.android.whitecaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UploadDatabase extends AppCompatActivity {
   Button uploadRoutineBtn,uploadTeacherBtn,uploadStudentBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_database);
        uploadRoutineBtn=(Button)findViewById(R.id.routine);
        uploadStudentBtn=(Button)findViewById(R.id.student);
        uploadTeacherBtn=(Button) findViewById(R.id.teacher);
        uploadRoutineBtn.setOnClickListener(new View.OnClickListener(){

                                                @Override
                                                public void onClick(View v)
                                                {
                                                    Intent intent = new Intent(UploadDatabase.this,UploadRoutine.class);
                                                    startActivity(intent);
                                                }
                                            }
        );
       uploadTeacherBtn.setOnClickListener(new View.OnClickListener()
       {

           @Override
           public void onClick(View v) {
               Intent intent = new Intent(UploadDatabase.this,UploadTeacher.class);
               startActivity(intent);
           }
       });
        uploadStudentBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadDatabase.this,UploadStudent.class);
                startActivity(intent);
            }
        });
    }
}
