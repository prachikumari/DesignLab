package com.example.android.whitecaps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChooseStream extends AppCompatActivity {
     private static Spinner sp;
     private  static EditText streamTxt;
     private  static Button addBtn,removeBtn,manageBtn;
     ArrayList <String> listItems = new ArrayList<String>();
     ArrayAdapter<String> dataAdapter1;
     String streamName;
     String[] name;
     InputStream inputStream= null;
     List list= new ArrayList<String>();//List of array to add
     BackgroundTask backgroundTask;
     Set<String> hs = new HashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_stream);
        sp=(Spinner)findViewById(R.id.dropdown);
        streamTxt=(EditText)findViewById(R.id.stream);
        addBtn=(Button)findViewById(R.id.add);
        removeBtn=(Button)findViewById(R.id.remove);
        manageBtn=(Button)findViewById(R.id.upload);

        //Intent intent;
        dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        sp.setAdapter(dataAdapter1);
        fetchData();
       // spinner_fn();
      // finish();
       addBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               add();
           }
        });
        manageBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String stream = sp.getSelectedItem().toString();
                if(stream != null) {
                    Intent intent = new Intent(ChooseStream.this, AddSemester.class);
                    intent.putExtra("stream", stream);
                    startActivity(intent);
                }else{
                    Toast.makeText(ChooseStream.this,"Please choose a stream first." , Toast.LENGTH_LONG).show();

                }


            }
        });

   }




    public  void  spinner_fn()
    {
       dataAdapter1=new ArrayAdapter<String>(ChooseStream.this,android.R.layout.simple_spinner_item);
        sp.setAdapter(dataAdapter1);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             sp.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public  void fetchData()
    {
        //ChooseStream chooseStream= new ChooseStream();
        backgroundTask =new BackgroundTask(this,list,sp,hs);
        String method="fetchData";
        backgroundTask.execute(method);
       // finish();


    }

    public  void setDataInSpiiner(String res) {
    }


         public void add() {
            // finish();
             streamName = streamTxt.getText().toString();
              backgroundTask = new BackgroundTask(ChooseStream.this,list,sp,streamTxt,hs);
             String method = "addStream";
             backgroundTask.execute(method, streamName);
            // finish();
         }
}
