package com.example.android.whitecaps;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ThreadFactory;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


//SplashScreen
public class SplashScreen extends AppCompatActivity implements NetworkStateChangeReceiver.ConnectivityReceiverListener{


    public ConstraintLayout cl;
    public CoordinatorLayout co;
    private Intent serviceIntent;
    String filename="";

    //String path =file.getAbsolutePath();
    String path ="";
    String name = "";
    String fname="";
    int pos = 0,pos1=0;
    String ext ="",imei="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {//execution starts  here


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damgr);

        cl = (ConstraintLayout) findViewById(R.id.constraintLayout);
        //co = (CoordinatorLayout)findViewById(R.id)
        serviceIntent = new Intent(getApplicationContext(),MyService.class);

        checkConnectivity();
        boolean f = false;
        try {
            f = getCurrentTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(f ==  true)
        {
            cache_read();
            chache_read2();
        }
        //cache_read();
        //
        // chache_read2();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // do something
                Intent intent = new Intent(SplashScreen.this, mainPage.class);//going to mainPage activity from current activity
                startActivity(intent);
            }
        }, 3000);//Splashscreen displays for duration of 2seconds

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean getCurrentTime() throws ParseException {
        Calendar calender = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        Date now= calender.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        String time1= date.format(now);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");//Time is in 24hr format
        Date d=sdf.parse(time1);
                String startTime = "12:00";
                String endTime = "14:00";
                Date d1 = sdf.parse(startTime);
                Date d2 = sdf.parse(endTime);

                long elapsed1 = d.getTime() - d1.getTime();
                long elapsed2 = d2.getTime() - d.getTime();
                if (elapsed1 >= 0 && elapsed2 >= 0) {
                    return true;
                }
                 else
                     return false;

    }




    private void checkConnectivity(){
        boolean isConnected  = NetworkStateChangeReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        if (isConnected){
            // Snackbar.make(cl, getString(R.string.internet_connected), Snackbar.LENGTH_LONG).show();
            Toast.makeText(this,getString(R.string.internet_connected),Toast.LENGTH_LONG).show();
            startService(serviceIntent);
            readData();
        }else {
            /*Snackbar.make(cl, getString(R.string.no_internet_connected), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.settings), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    }).setActionTextColor(Color.RED)
                    .show();*/
            Toast.makeText(this,getString(R.string.no_internet_connected),Toast.LENGTH_LONG).show();
        }
    }

    Context p = SplashScreen.this;
    private  int PICK_CSV_REQUEST=1;
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    //Uri to store the image uri
    private Uri filePath;

    private void readData() {
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice;
        tmDevice = "" + tm.getDeviceId();
        final String actor = "";

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                File dir = p.getCacheDir();  //return the directory of internal cache in which your file will be created

                //File file = new File(dir,"File_"+tmDevice+".csv");  //Creates a new file named MyFile.txt in a folder "dir"

                if (dir.exists()) {
                    for (File f : dir.listFiles()) {
                        path = f.getAbsolutePath();
                        Log.e("path",path);
                        name = f.getName();
                        Log.e("name",name);
                        pos = name.lastIndexOf(".");
                        String actor="";
                        if (pos > 0) {
                            fname = name.substring(0, pos);
                        }
                        pos1 = fname.lastIndexOf("_");
                        if (pos1 > 0) {
                            actor = fname.substring(0, pos1);
                        }
                        Log.e("actor",actor);
                        ext = name.substring(pos+1,name.length());
                        imei = fname.substring(pos1+1,fname.length());

                        Log.e("imei",imei);
                        Log.e("ext",ext);
                        if(imei.equals(tmDevice)){
                            filename=name;
                            //break;
                        }

                //String UPLOAD_URL = "http://192.168.0.107/android_connect/upload_cache.php";

                File file = new File(dir,filename);
                if (path == null) {
                    //Toast.makeText(this, "Sorry", Toast.LENGTH_LONG).show();
                } else {
                    if(actor.equals("student")){
                    OkHttpClient client = new OkHttpClient();
                    RequestBody file_body = RequestBody.create(MediaType.parse(ext),file);

                    RequestBody request_body = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("type",ext)
                            .addFormDataPart("uploaded_file",name,file_body).build();

                    Request request = new Request.Builder()
                            .url("http://192.168.43.109/android_connect/student_upload_cache.php")
                            .post(request_body)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                        if(!response.isSuccessful()){
                            throw new IOException("Error : "+response);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }}

                    if(actor.equals("teacher")){
                        OkHttpClient client = new OkHttpClient();
                        RequestBody file_body = RequestBody.create(MediaType.parse(ext),file);

                        RequestBody request_body = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("type",ext)
                                .addFormDataPart("uploaded_file",name,file_body).build();

                        Request request = new Request.Builder()
                                .url("http://192.168.43.109/android_connect/teacher_upload_cache.php")
                                .post(request_body)
                                .build();

                        try {
                            Response response = client.newCall(request).execute();
                            if(!response.isSuccessful()){
                                throw new IOException("Error : "+response);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }}


            }}}
            }
        });

        t.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityReceiver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onNetworkChange(boolean inConnected) {
        showSnack(inConnected);
    }



    private void cache_read() {
        BackgroundTask backgroundtask = new BackgroundTask(this);
        String method = "addteachercache";
        backgroundtask.execute(method , imei , ext);
    }

    private void chache_read2() {
        BackgroundTask backgroundtask = new BackgroundTask(this);
        String method = "addstudentcache";
        backgroundtask.execute(method , imei , ext);
    }



}
