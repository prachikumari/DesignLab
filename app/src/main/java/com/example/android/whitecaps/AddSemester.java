package com.example.android.whitecaps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//This activity class is used to add semester for the given stream
public class AddSemester extends AppCompatActivity {

            EditText semesterTxt;
            List<String> list = new ArrayList<>();
            Set<String> hs = new HashSet<>();
            Spinner semesterSpinner;
            String semester;
            Button addbutton ;
            String stream;
            BackgroundTask  backgroundTask;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_add_semester);
                Intent intent = getIntent();
                stream = intent.getStringExtra("stream");
                semesterTxt=(EditText)findViewById(R.id.semester);
                semesterSpinner = (Spinner) findViewById(R.id.semdropdown);

                fetchData();

                addbutton = (Button) findViewById(R.id.add);
                addbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                 public void onClick(View v) {
                  //showsalert if added semester lies out of range of 1 to 8
                 if(Integer.parseInt(semesterTxt.getText().toString())>8 || Integer.parseInt(semesterTxt.getText().toString())<1)
                    showalert("Range between 1 to 8");
                 else
                    add();//adds semester to the database if user enters between 1 to 8
                  }
                 });

            }

            //showalert function
            public void showalert(String msg)
            {

                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage(msg);
                alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();

            }

            //fetches existing semester(if any) for a given stream
            public  void fetchData()
            {
                backgroundTask =new BackgroundTask(this,stream,list,hs , semesterSpinner);
                String method="fetchSemester";
                backgroundTask.execute(method);

            }

            //adds semester for a given stream
            public void add()
            {
                semester = semesterTxt.getText().toString().trim();
                if(list.contains(semester))
                    showalert("Given semester aready exist");
                else {
                        backgroundTask = new BackgroundTask(this, stream, list, hs, semesterSpinner, semesterTxt);
                        String method = "addSemester";
                        backgroundTask.execute(method, semester);
                     }
            }

}
