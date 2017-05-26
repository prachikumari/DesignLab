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
import java.util.UUID;

/**
 * Created by User on 03-05-2017.
 */
//MyLocationListener

public class FileManager {

    Context p;
    String Tresult;
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

    public void SaveInInternalCacheStorage(String imeiNo) {
        File dir = p.getCacheDir();  //return the directory of internal cache in which your file will be created
        File file = new File(dir,"File_"+imeiNo+".csv");  //Creates a new file named MyFile.txt in a folde "dir"
        writeData(file,Tresult);  //Calling user defined method

    }
    public void LoadFromInternalCacheStorage(String imeiNo) {
        File dir = p.getCacheDir();  //return the directory of internal cache in which your file will be created
        File file = new File(dir,"File_"+imeiNo+".csv");  //Creates a new file named MyFile.txt in a folder "dir"
        readData(file);  //Calling user defined method
    }

    //Writing data into file
    public void writeData(File file, String text1)
    {
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream = new FileOutputStream(file);

            //writing the text into the file in the form of char[] bytes ; getBytes will convert string to char[] bytes
            //Text contains OTP,Period,startTimeOtp,endTimeOtp and location of user
            fileOutputStream.write(text1.getBytes());
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
    public void readData(File file) {
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
            Log.e("Showdata",showdata);
             doFileUpload(file);
        } catch (FileNotFoundException e) {e.printStackTrace();
        } catch (IOException e) {e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {e.printStackTrace();}
            }
        }


    }

    private void doFileUpload(File file) throws FileNotFoundException {
        String UPLOAD_URL = "http://192.168.0.106/android_connect/upload.php";

        //if (path == null) {
       // } else {
            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();

                //Creating a multi part request
                /*new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                        .addFileToUpload(path, "csv") //Adding file
                        .addParameter("name", name) //Adding text parameter to the request
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload*/


            } catch (Exception exc) {
                Log.e("Exception",exc.getMessage());
            }
     //   }
    }


}




