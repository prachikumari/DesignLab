package com.example.android.whitecaps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by User on 30-04-2017.
 */
//RoutineMgr

public class RoutineMgr  {
    LoginScreen mr;
    private String selectedStream;
    private String selectedSemester;
    private String selectedSection;
    private  String teacherName;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(String selectedDay) {
        this.selectedDay = selectedDay;
    }

    private String selectedPeriod;
    private String selectedDay;
    //DataMgr dataMgr;
    private String  period,day;
    TakeAttendance ta;
    private ArrayList<String> stream,semester,section;
    DataMgr dataMgr;
    Context context;
    Routine routine;
    Subject subject;
    Teacher teacher;
   // private String tname;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    private double latitude,longitude;

  public  RoutineMgr(Context context,Teacher teacher,double latitude,double longitude)
  {
      dataMgr = new DataMgr(context);
      this.context=context;
      this.teacher=teacher;
      this.latitude=latitude;
      this.longitude=longitude;

  }
  public  void getAllStream(Spinner myspinner)
  {
      stream = dataMgr.getAllStreams();
      ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
              android.R.layout.simple_spinner_item, stream);

      // Drop down layout style - list view with radio button
      dataAdapter
              .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

      // attaching data adapter to spinner
      myspinner.setAdapter(dataAdapter);

  }
    public  void getAllSemester(Spinner myspinner,String stream1)
    {
        semester = dataMgr.getAllSemester(stream1);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, semester);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        myspinner.setAdapter(dataAdapter);

    }

    public  void getAllSection(Spinner myspinner,String stream1,String semester1)
    {
        section = dataMgr.getAllSections(stream1,semester1);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, section);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        myspinner.setAdapter(dataAdapter);

    }
    //Getter and Setter
  //  public String getString() {
    //    return useremail;
  //  }

//    public void setString(String h) {
        //useremail=h;
   // }

   // public String getStream(Spinner mySpinner) {
     //   return stream;
   // }

   // public void setStream(String stream) {
     //   this.stream = stream;
   // }



    public void showalert(String s)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Missing Fields");
        alertDialog.setMessage("Please Enter" + s);
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // return;
            }
        });
        alertDialog.show();
    }


    //Checking Empty fields if any
    public boolean checkEmptyFields(Subject subject) {
        this.subject = subject;
        if(getSelectedStream().equals("Stream") || getSelectedSemester().equals("Semester") ||
                getSelectedSection().equals("Section") || getSelectedPeriod().equals("Period"))
        {
            return false;
        }
      else
        {
            routine= new Routine();
            dataMgr.getAllroutine(getSelectedDay(),getSelectedStream(),getSelectedSemester(),getSelectedSection(),routine);
            String subjectTechercode = null;
            if(getSelectedPeriod().equals("1"))
             subjectTechercode = routine.getPeriod1();
            else  if(getSelectedPeriod().equals("2"))
                subjectTechercode = routine.getPeriod2();
            else  if(getSelectedPeriod().equals("3"))
                subjectTechercode = routine.getPeriod3();
            else  if(getSelectedPeriod().equals("4"))
                subjectTechercode = routine.getPeriod4();
            else  if(getSelectedPeriod().equals("5"))
                subjectTechercode = routine.getPeriod5();
            else  if(getSelectedPeriod().equals("6"))
                subjectTechercode = routine.getPeriod6();
            //Seprating subjectcode from teachercode e.g EE101
             String subname2 = subjectTechercode.substring(0, subjectTechercode.indexOf("("));


            //Seprating teachercode e.g SMU
           // String tcode = subjectTechercode.substring(subjectTechercode.indexOf("(") + 1, subjectTechercode.indexOf(")"));

            //Extracting subjectname corresponding to subjectcode extracted now

            dataMgr.getsubname(subname2,subject);


            //Extracting teachername corresponding to teachercode extracted now
              subject.settName(getTeacherName(subject.gettCode()));
            return true;
            //showCnfirmation with a message

              //dataMgr.showConfirmation(subject.getSubName(), tname);


        }

    }
    public String getTeacherName(String s)
    {

        return dataMgr.gettname(s);
    }

    public String getSelectedStream() {
        return selectedStream;
    }

    public void setSelectedStream(String selectedStream) {
        this.selectedStream = selectedStream;
    }

    public String getSelectedSemester() {
        return selectedSemester;
    }

    public void setSelectedSemester(String selectedSemester) {
        this.selectedSemester = selectedSemester;
    }

    public String getSelectedSection() {
        return selectedSection;
    }

    public void setSelectedSection(String selectedSection) {
        this.selectedSection = selectedSection;
    }

    public String getSelectedPeriod() {
        return selectedPeriod;
    }

    public void setSelectedPeriod(String selectedPeriod) {
        this.selectedPeriod = selectedPeriod;
    }
    String mClassId;

    public String getClassId() {
        return mClassId;
    }

    public void setClassId(String classId) {
        mClassId = classId;
    }

    /*public void showConfirmation(final String subject1, String tname)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle("Confirmation");
         alertDialog.setMessage(subject1 + " for " + selectesStream + "," + " Semester " +  selectesSemester + " Section" +
                 selectesSection + "during" + "Period" + selectesPeriod + "is assigned to" + tname);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
    public void onClick(DialogInterface dialog, int which) {
        DigitalAttendanceMgr atmr = new DigitalAttendanceMgr();
        String classid = dataMgr.getclssid(selectesStream,selectesSemester,selectesSection);
        atmr.attenMgr.generateOTP(classid,selectesPeriod ,day , context,teacher,RoutineMgr.this );

        //              // if yes clicked generateOTP in AttenMgr is called
        //            atr.generateOTP(routinemgr.getDay(), routinemgr.getPeriod(), routinemgr.getStream(mySpinner),
        //                  routinemgr.getSemester(), routinemgr.getSection(), dataMgr, TakeAttendance.this);
    }
    });
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int which) {
            //  if no clicked values are set to default

                dialog.cancel();
        }
    });

    // Showing Alert Message
        alertDialog.show();
}*/

}



