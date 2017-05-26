package com.example.android.whitecaps;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;
//TakeAttendance Page
public class TakeAttendance extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button showDateBtn;
    //AttenMgr atr;
    int year_x, month_x, day_x;
    static final int DIALOG_ID = 0;
    private TextView subtxt;
    private Button otpbutton;
    RoutineMgr routinemgr;
    DataMgr helper;
    private TextView datefield;
    static Spinner mySpinner;
    static Spinner mySpinner1;
    static Spinner mySpinner2;
    static Spinner mySpinner3;
    //String usermail;
    AttenMgr mAttenMgr;
    double latitude,longitude;
Teacher teacher;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {//execution starts here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        Intent newintent = getIntent();
        teacher = newintent.getParcelableExtra("teacher");//retrieving AttenMgr object
        //usermail = atr.getUseremail();
        latitude = newintent.getDoubleExtra("latitude",0.0);
        longitude= newintent.getDoubleExtra("longitude",0.0);
        //shows the current date by datepicker
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        datefield = (TextView) findViewById(R.id.textView6);
        int m=month_x+1;
        datefield.setText(day_x + "/" + m + "/" + year_x);
        //dataMgr = new DataMgr(TakeAttendance.this);

        mySpinner = (Spinner) findViewById(R.id.spinner1);// stream
        mySpinner1 = (Spinner) findViewById(R.id.spinner2);//semester
        mySpinner3 = (Spinner) findViewById(R.id.spinner4);//period
        mySpinner2 = (Spinner) findViewById(R.id.spinner3);//section
        mySpinner.setOnItemSelectedListener(TakeAttendance.this);
        mySpinner1.setOnItemSelectedListener(TakeAttendance.this);
        otpbutton = (Button) findViewById(R.id.button2);

       // final String stream = mySpinner.getSelectedItem().toString();
        routinemgr= new RoutineMgr(this , teacher , latitude,longitude);

        routinemgr.getAllStream(mySpinner);
        // routinemgr.loadStreamListInDropdown();
       // final String semester = mySpinner1.getSelectedItem().toString();


        showDialogOnButtonClick();
        otpbutton.setOnClickListener(//on GenerateOTP Button Click
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // String section= "Section";
                       // section = mySpinner2.getSelectedItem().toString();
                        //String period = mySpinner3.getSelectedItem().toString();
                        String d = datefield.getText().toString();
                        SimpleDateFormat inFormat = new SimpleDateFormat("dd/M/yyyy");
                        Date date = null;
                        try
                        {
                            date = inFormat.parse(d);
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                        String goal = outFormat.format(date);//extracting day from date
                        routinemgr.setSelectedStream(mySpinner.getSelectedItem().toString());
                        routinemgr.setSelectedSemester(mySpinner1.getSelectedItem().toString());
                        routinemgr.setSelectedSection(mySpinner2.getSelectedItem().toString());
                        routinemgr.setSelectedPeriod(mySpinner3.getSelectedItem().toString());
                        routinemgr.setSelectedDay(goal);
                        mAttenMgr = new AttenMgr(TakeAttendance.this,routinemgr);
                        mAttenMgr.generateOTP();
                        //mAttenMgr= new AttenMgr();

                         //routinemgr.chckEmptyFields(mySpinner,mySpinner1,mySpinner2,mySpinner3,goal);
                        //Extracting subjectcode along with teachercode from routine database e.g EE101(SMU)
                        //String subname1 = dataMgr.getSub(routinemgr.getStream(mySpinner), routinemgr.getSemester(),
                          //      routinemgr.getSection(), routinemgr.getPeriod(), routinemgr.getDay());

                        //Seprating subjectcode from teachercode e.g EE101
                       // String subname2 = subname1.substring(0, subname1.indexOf("("));

                        //Seprating teachercode e.g SMU
                        //String tcode = subname1.substring(subname1.indexOf("(") + 1, subname1.indexOf(")"));

                        //Extracting subjectname corresponding to subjectcode extracted now
                      //  String subname = dataMgr.getsubname(subname2);

                        //Extracting teachername corresponding to teachercode extracted now
                        //String tname = dataMgr.gettname(tcode);

                        //showCnfirmation with a message
                        //showConfirmation(subname, tname);
                    }
                }
        );


    }

    public void showDialogOnButtonClick() {
        showDateBtn = (Button) findViewById(R.id.button4);
        showDateBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListner, year_x, month_x, day_x);
        return null;
    }

    public DatePickerDialog.OnDateSetListener dpickerListner = new DatePickerDialog.OnDateSetListener() {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month + 1;
            day_x = dayOfMonth;

            showalertdate(dayOfMonth, month_x, year_x);
            //datefield = (TextView) findViewById(R.id.textView6);
            datefield.setText(dayOfMonth + "/" + month_x + "/" + year);

        }
    };


    public static void reset()
    {
        mySpinner.setSelection(0);
        mySpinner1.setSelection(0);
        mySpinner2.setSelection(0);
        mySpinner3.setSelection(0);

    }
    //alert is shown if teacher chooses date other than current date
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showalertdate(int day, int month, int year)
    {
        final Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DAY_OF_MONTH);
        if (d != day || month != m + 1 || y != year)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Date different from current date");
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // return;
                }
            });
            alertDialog.show();
        } else
            return;

    }

    //showalert


    //showconfirmation
   /* public void showConfirmation(String subject, String tname)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Confirmation");
       // alertDialog.setMessage(subject + " for " + routinemgr.getStream(mySpinner) + "," + " Semester " +  routinemgr.getSemester() + " Section" + routinemgr.getSection() + "during" + "Period" + routinemgr.getPeriod() + "is assigned to" + tname);
//        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

  //              // if yes clicked generateOTP in AttenMgr is called
    //            atr.generateOTP(routinemgr.getDay(), routinemgr.getPeriod(), routinemgr.getStream(mySpinner),
      //                  routinemgr.getSemester(), routinemgr.getSection(), dataMgr, TakeAttendance.this);
            }
        //});

        /* Setting Negative "NO" Button
        //alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
          //  public void onClick(DialogInterface dialog, int which) {
                //  if no clicked values are set to default
                mySpinner.setSelection(0);
                mySpinner1.setSelection(0);
                mySpinner3.setSelection(0);
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {  int id1 = parent.getId();
           switch(id1) {

               case R.id.spinner1:
                   routinemgr.getAllSemester(mySpinner1,String.valueOf(mySpinner.getSelectedItem()));
                   break;

               case R.id.spinner2:
                   routinemgr.getAllSection(mySpinner2,String.valueOf(mySpinner.getSelectedItem()), String.valueOf(mySpinner1.getSelectedItem()));
                   break;
           }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}
