package com.example.android.whitecaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Manage_Database extends AppCompatActivity {
    private static Button uploadRoutineBtn;

    DigitalAttendanceMgr dam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_database);
        uploadRoutineBtn=(Button)findViewById(R.id.routine);
       /// Intent intent = getIntent();
        //String stream = intent.getStringExtra("stream");
       // dam.dm.showChooseStream(this);
        buttonFunction();

    }
    public void buttonFunction()
    {
        dam= new DigitalAttendanceMgr();
        uploadRoutineBtn.setOnClickListener(//on clicking login button
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dam.dm.showChooseStream(Manage_Database.this);

                    }
                }
        );
    }

}
