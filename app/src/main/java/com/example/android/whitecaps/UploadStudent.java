package com.example.android.whitecaps;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;



public class UploadStudent extends AppCompatActivity implements AdapterView.OnItemSelectedListener,SingleUploadBroadcastReceiver.Delegate {
    Spinner spinnerStream, spinnerSem;
    BackgroundTask backgroundTask;
    List<String> list = new ArrayList<>();
    ArrayList<String> list1 = new ArrayList<>();
    Set<String> hs = new HashSet<>();
    Set<String> hs1 = new HashSet<>();
    String stream;
    public static final String UPLOAD_URL = "http://192.168.43.109/android_connect/upload_student.php";
    //Pdf request code
    //private int PICK_PDF_REQUEST = 1;
    private  int PICK_CSV_REQUEST=1;
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    //Uri to store the image uri
    public Uri filePath = null;
    Button buttonChoose,buttonUpload,downloadtemplate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_student);
        buttonChoose = (Button) findViewById(R.id.choosebutton);
        buttonUpload = (Button) findViewById(R.id.uploadbutton);
        spinnerStream = (Spinner) findViewById(R.id.stream1);
        spinnerSem = (Spinner) findViewById(R.id.semester1);
        downloadtemplate = (Button)findViewById(R.id.template);
        spinnerStream.setOnItemSelectedListener(UploadStudent.this);

        //  editText = (EditText) findViewById(R.id.editTextName);
        //Setting clicklistener
        fetchData();
        spinnerStream.setOnItemSelectedListener(UploadStudent.this);
        buttonChoose.setOnClickListener(
                new View.OnClickListener(

                ) {

            @Override
            public void onClick(View v) {
                if(list.isEmpty())
                    Toast.makeText(UploadStudent.this,"go to manage dataase ",Toast.LENGTH_SHORT).show();
                if(list1.isEmpty())
                    Toast.makeText(UploadStudent.this,"go to manage database ",Toast.LENGTH_SHORT).show();
                else
                showFileChooser();
            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                uploadMultipart(UploadStudent.this);
            }
        });
        downloadtemplate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                copyAssets();
            }
        });

    }
    public void fetchData() {
        backgroundTask = new BackgroundTask(this, spinnerStream, list, hs);
        String method = "fetchStream";
        backgroundTask.execute(method);

    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        stream = spinnerStream.getSelectedItem().toString();
        backgroundTask = new BackgroundTask(this, spinnerSem, list1, hs1, stream);
        String method = "fetchSem";
        backgroundTask.execute(method);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private static final String TAG = "AndroidUploadService";

    private final SingleUploadBroadcastReceiver uploadReceiver = new SingleUploadBroadcastReceiver();

    @Override
    protected void onResume() {
        super.onResume();
        uploadReceiver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        uploadReceiver.unregister(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void uploadMultipart(Context context) {
        if(filePath == null) {
            Toast.makeText(this, "Please select a file and retry", Toast.LENGTH_LONG).show();
        }
        else{
            String stream = spinnerStream.getSelectedItem().toString().trim().toUpperCase();
            String sem=spinnerSem.getSelectedItem().toString().trim();
            String name= stream+"_"+"SEM_"+sem+"_db";
        String path = FilePath.getPath(this, filePath);
        //getting name for the image
        if (path == null) {
            Toast.makeText(this, "Choose file and retry", Toast.LENGTH_LONG).show();
        } else {
           // String name = "Teacher_db";
            //getting the actual path of the image


            if (path == null) {

                Toast.makeText(this, "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
            } else {
                //Uploading code
                try {
                    String uploadId = UUID.randomUUID().toString();
                    uploadReceiver.setDelegate(this);
                    uploadReceiver.setUploadID(uploadId);
                    //Creating a multi part request
                    new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                            .addFileToUpload(path, "csv") //Adding file
                            .addParameter("name", name) //Adding text parameter to the request
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(2)
                            .startUpload(); //Starting the upload


                } catch (Exception exc) {
                    Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }}
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
    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("text/csv");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select CSV file"), PICK_CSV_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CSV_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
        }
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onProgress(int progress) {

    }

    @Override
    public void onProgress(long uploadedBytes, long totalBytes) {

    }

    @Override
    public void onError(Exception exception) {

    }

    @Override
    public void onCompleted(int serverResponseCode, byte[] serverResponseBody) {
        String h=null;
        int i=0;
        try {
            JSONObject response = new JSONObject(new String(serverResponseBody, "iso-8859-1"));
            h = response.getString("yo");
            showalert(h);
            Toast.makeText(this,h,Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancelled() {

    }
    private void copyAssets()
    {


        AssetManager assetManager = this.getAssets();
        InputStream in = null;
        try {
            in = assetManager.open("Template_ROUTINE.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File SDCardRoot = new File(Environment.getExternalStorageDirectory().toString()+"/DigitalAttendance");
        SDCardRoot.mkdirs();
        File file = new File(SDCardRoot,"templateRoutine.csv");
        FileOutputStream fileOutput = null;
        try {
            fileOutput = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[1024];
        int bufferLength = 0;
        try {
            while((bufferLength = in.read(buffer)) > 0)
            {
                fileOutput.write(buffer, 0, bufferLength);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showalert("Check Internal Memory DigialAttendance Folder");
    }

}
