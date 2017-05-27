package com.example.android.whitecaps;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Created by User on 03-05-2017.
 */
//MyLocationListener

public class FileManager {

    Context p;
    String Tresult;
    boolean append=false;
    //String TOTP,TPeriod,TstartTimeOtp,TendTimeOtp;
    public FileManager(Context context,String result){
        p=context;
        // String OTP,Period,startTimeOtp,endTimeOtp;
        ;
        Tresult=result;
    }

    public FileManager(Context context){
        p=context;
    }

    public void SaveInInternalCacheStorage(String imeiNo,String actor) {
        File dir = p.getCacheDir();  //return the directory of internal cache in which your file will be created
        File file = new File(dir,actor+"_"+imeiNo+".csv");  //Creates a new file named MyFile.txt in a folde "dir"
        writeData(file,Tresult,imeiNo,actor);  //Calling user defined method

    }

    public int LoadFromInternalCacheStorage(String imeiNo,String actor) {
        File dir = p.getCacheDir();  //return the directory of internal cache in which your file will be created
        File file = new File(dir,actor+"_"+imeiNo+".csv");  //Creates a new file named MyFile.txt in a folder "dir"
        int bl = readData(file);  //Calling user defined method
        return bl;
    }

    DigitalAttendanceMgr digitalAttendanceMgr;

    //Writing data into file
    public void writeData(File file, String text1,String imei,String actor)
    {
        FileOutputStream fileOutputStream=null;
        int val = LoadFromInternalCacheStorage(imei,actor);
        try {
            if(val == 1){
                append = true;
            }
            fileOutputStream = new FileOutputStream(file,append);

            //writing the text into the file in the form of char[] bytes ; getBytes will convert string to char[] bytes
            //Text contains OTP,Period,startTimeOtp,endTimeOtp and location of user
            if(val == 0 && actor.equals("teacher")){
                fileOutputStream.write("Date,Otp,Latitude,Longitude,Start_time,End_time,Stream,Semester,Section,Period".getBytes());
                fileOutputStream.write("\n".getBytes());
            }
            if(val == 0 && actor.equals("student")){
                fileOutputStream.write("Date,Time,Latitude,Longitude,Otp,Student_id,Student_name,Semester,Stream,Section,imei,androidid".getBytes());
                fileOutputStream.write("\n".getBytes());
            }

            fileOutputStream.write(text1.getBytes());
            fileOutputStream.write("\n".getBytes());
        } catch (FileNotFoundException e) {e.printStackTrace();
        } catch (IOException e) {e.printStackTrace();}
        finally {
            if(fileOutputStream!=null)
            {
                try {
                    fileOutputStream.close();    //closing the file
                } catch (IOException e) {e.printStackTrace();}
            }
        }

    }


    //Reading data from file
    public int readData(File file) {
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
            int read = -1;
            StringBuffer stringBuffer = new StringBuffer();//to store a string (which is retrieved by Internal Storage)
            while ((read = fileInputStream.read()) != -1)//read() will return ascii value (char[] bytes) and returns -1 if bytes not found
            {
                stringBuffer.append((char) read);//Converting ascii into char by typecasting
            }
            String showdata = stringBuffer.toString();
            String[] lines;

            String regex= "\\n";
            lines = showdata.split(regex);
            String data = lines[1];
            Log.e("Showdata",showdata);

            StringTokenizer st2 = new StringTokenizer(data, ",");
            String dt="";
            while (st2.hasMoreElements()) {
                dt = (String) st2.nextElement();
                Log.e("hello ",dt);
                break;
            }
            Log.e("date",dt);
            digitalAttendanceMgr = new DigitalAttendanceMgr();
            String date1 = digitalAttendanceMgr.getCurrentDate();
            Log.e("today",date1);
            if(dt.compareTo(date1) == 0){
                return 1;
            }

        } catch (FileNotFoundException e) {e.printStackTrace();
        } catch (IOException e) {e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {e.printStackTrace();}
            }
        }

    return 0;
    }
}




