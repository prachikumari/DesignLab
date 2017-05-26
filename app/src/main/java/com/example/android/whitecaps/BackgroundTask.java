package com.example.android.whitecaps;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.R.id.list;

/**
 * Created by User on 13-05-2017.
 */
//This is a non-activity java class use to do background task
//This class enables the user to access php page to connect to the server ultimately
public class BackgroundTask extends AsyncTask<String,Void,String> {

        Context context;
        String response ="";
        String line="";
        String[] name;
        ChooseStream chooseStream;
        List list , list2 , streamlist;
        ArrayList<String> semlist;
        Spinner sp,sem,spinnerStream,spinnerSem;
        String stream=null,semester=null;
        EditText streamTxt,semesterTxt;
        ArrayAdapter<String> dataAdapter1,dataAdapter2;
        Set<String> hs , hs2 , streamset,semset;
        String selectedStream,selectedStreamUpload;
        String fname;
        private String  templateurl;

        //Various Constructors with different parmeters
        BackgroundTask(Context context, List list1, Spinner sp1, EditText streamTxt,Set hs1)
        {
            this.context= context;
            list = list1;
            sp=sp1;
            this.streamTxt=streamTxt;
            hs = hs1;
        }
        BackgroundTask(String u)
        {

            this.templateurl=u;
        }
        BackgroundTask(Context context,List list1,Spinner sp1,Set hs1)
        {
            this.context= context;
            list = list1;
            sp=sp1;
            hs = hs1;
        }
        BackgroundTask(Context context , Spinner sp , List list, Set s)
        {
            this.context = context;
            spinnerStream=sp;
            streamlist=list;
            streamset=s;

        }
        BackgroundTask(Context context, String nam)
        {
            this.context=context;
            this.fname=nam;
        }

        BackgroundTask(Context context , Spinner sp , ArrayList list, Set s, String stream)
        {
            this.context = context;
            spinnerSem=sp;
            semlist=list;
            semset=s;
            selectedStreamUpload=stream;
            semlist.clear();
        }
        BackgroundTask(Context context,String selectedstream, List list1,Set hs1,Spinner semester)
        {
            this.context= context;
            selectedStream = selectedstream;
            list2 = list1;
            hs2 = hs1;
            sem = semester;
        }
        BackgroundTask(Context context,String selectedstream, List list1,Set hs1,Spinner semester,EditText s)
        {
            this.context= context;
            //semesterTxt = semesterText;
            selectedStream = selectedstream;
            list2 = list1;
            hs2 = hs1;
            sem = semester;
            semesterTxt=s;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            //URL's of Php page
            String up_url = "http://192.168.43.109/android_connect/insert.php";
            String fetch_url = "http://192.168.43.109/android_connect/fetchData.php";
            String fetch_semester_url = "http://192.168.43.109/android_connect/fetchSemester.php";
            String add_semester_url = "http://192.168.43.109/android_connect/insertSem.php";
            String fetch_stream_url = "http://192.168.43.109/android_connect/fetchStream.php";
            String compare_url = "http://192.168.43.109/android_connect/compare.php";
            String result = null;
            String method = params[0];

            //Process starts here depending upon the method name
            //here we are taking stream as input from user and add that stream to the database
            if (method.equals("addStream")) {
                stream = params[1].toUpperCase();
                try {
                    URL url = new URL(up_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                    String data = URLEncoder.encode("Stream","UTF-8")+ "=" + URLEncoder.encode(stream,"UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();
                    //InputStream to get response from server
                    InputStream is = httpURLConnection.getInputStream();
                    is.close();
                    result= "Insertion successfull";
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            //here we are taking semester as input from user and add that semester to the database
            else  if (method.equals("addSemester")) {
                semester = params[1];
                try {
                    URL url = new URL(add_semester_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    //String data = URLEncoder.encode("Stream", "UTF-8") + "=" + URLEncoder.encode(stream, "UTF-8");
                   String data = URLEncoder.encode("Stream","UTF-8")+"="+URLEncoder.encode(selectedStream,"UTF-8") +"&"+
                            URLEncoder.encode("Semester","UTF-8")+"="+URLEncoder.encode(semester.trim(),"UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();
                    //Read the response
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    response = stringBuilder.toString();
                    httpURLConnection.disconnect();
                    result= "Insertion sem successfull";
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            //checking if routine that we are uploading is valid or not
            else if(method.equals("compare_routine"))
            {
                try {
                    URL url = new URL(compare_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //To get Response from server
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(fname, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    response = stringBuilder.toString();
                    httpURLConnection.disconnect();
                    Log.e("RESPONSETAG", response);
                    // return response;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                result= "hii";

            }
            //here we are fetching semester already present in the database
            else if(method.equals("fetchSemester"))
            {

                    try {
                        URL url = new URL(fetch_semester_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        //To get Response from server
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        OutputStream os = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        String data = URLEncoder.encode("Stream", "UTF-8") + "=" + URLEncoder.encode(selectedStream, "UTF-8");
                        bufferedWriter.write(data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        os.close();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                        StringBuilder stringBuilder = new StringBuilder();
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line + "\n");
                        }
                        bufferedReader.close();
                        inputStream.close();
                        response = stringBuilder.toString();
                        httpURLConnection.disconnect();
                        Log.e("RESPONSETAG", response);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        //Php returns the fetched data in the form of json object
                        //so here we are extracting data from json object

                        JSONArray JA = new JSONArray(response);
                        JSONObject json = null;
                        int i;
                        String  semester1 = "", semester2 = "", semester3 = "", semester4 = "", semester5 = "", semester6 = "", semester7 = "",
                                semester8 = "";
                        name = new String[JA.length()];
                        for (i = 0; i < JA.length(); i++) {
                            json = JA.getJSONObject(i);
                            semester1 += json.getString("Semester1");
                            semester2 += json.getString("Semester2");
                            semester3 += json.getString("Semester3");
                            semester4 += json.getString("Semester4");
                            semester5 += json.getString("Semester5");
                            semester6 += json.getString("Semester6");
                            semester7 += json.getString("Semester7");
                            semester8 += json.getString("Semester8");

                        }

                        if (semester1.equals("1"))
                            list2.add("1");

                        if (semester2.equals("1"))
                            list2.add("2");

                        if (semester3.equals("1"))
                            list2.add("3");

                        if (semester4.equals("1"))
                            list2.add("4");

                        if (semester5.equals("1"))
                            list2.add("5");

                        if (semester6.equals("1"))
                            list2.add("6");

                        if (semester7.equals("1"))
                            list2.add("7");

                        if (semester8.equals("1"))
                            list2.add("8");

                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                result= "fetchsemester";
            }

             else if (method.equals("fetchtemplate")){

                int count;
                try {
                    URL url = new URL(templateurl);
                    URLConnection conection = url.openConnection();
                    conection.connect();
                    // getting file length
                    int lenghtOfFile = conection.getContentLength();

                    // input stream to read file - with 8k buffer
                    InputStream input = new BufferedInputStream(url.openStream(), 8192);

                    // Output stream to write file
                    OutputStream output = new FileOutputStream("internal://template.csv");

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                      //  publishProgress(""+(int)((total*100)/lenghtOfFile));

                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();

                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }

                result="downloadsuccess";


             }
            // fetching semester from the database for the given stream
            else if(method.equals("fetchSem"))
            {
                try {
                    URL url = new URL(fetch_semester_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //get response from server
                    //To get Response from server
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("Stream", "UTF-8") + "=" + URLEncoder.encode(selectedStreamUpload, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    response = stringBuilder.toString();
                    httpURLConnection.disconnect();
                    Log.e("RESPONSETAG", response);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    semlist.clear();
                    JSONArray JA = new JSONArray(response);
                    JSONObject json = null;
                    int i;
                    String  semester1 = "", semester2 = "", semester3 = "", semester4 = "", semester5 = "", semester6 = "", semester7 = "",
                            semester8 = "";
                    name = new String[JA.length()];
                    for (i = 0; i < JA.length(); i++) {
                        json = JA.getJSONObject(i);
                        semester1 += json.getString("Semester1");
                        semester2 += json.getString("Semester2");
                        semester3 += json.getString("Semester3");
                        semester4 += json.getString("Semester4");
                        semester5 += json.getString("Semester5");
                        semester6 += json.getString("Semester6");
                        semester7 += json.getString("Semester7");
                        semester8 += json.getString("Semester8");

                    }
                    //Adding the fetched semester to the list
                    if (semester1.equals("1"))
                        semlist.add("1");

                    if (semester2.equals("1"))
                        semlist.add("2");

                    if (semester3.equals("1"))
                        semlist.add("3");

                    if (semester4.equals("1"))
                        semlist.add("4");

                    if (semester5.equals("1"))
                        semlist.add("5");

                    if (semester6.equals("1"))
                        semlist.add("6");

                    if (semester7.equals("1"))
                        semlist.add("7");

                    if (semester8.equals("1"))
                        semlist.add("8");

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                result= "fetchsemupload";

            }
            //fetching stream
            else if(method.equals("fetchStream"))
            {
                try {
                    URL url = new URL(fetch_stream_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    //To get Response from server
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    response = stringBuilder.toString();
                    httpURLConnection.disconnect();
                    Log.e("RESPONSETAG", response);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                       //fetching stream from json
                        JSONArray JA = new JSONArray(response);
                        JSONObject json = null;
                        int i;
                        name = new String[JA.length()];
                        for (i = 0; i < JA.length(); i++) {
                            json = JA.getJSONObject(i);
                            name[i] = json.getString("Stream");

                        }

                        for (i = 0; i < name.length; i++) {
                            streamlist.add(name[i]);
                        }

                    } catch (JSONException e) {
                       e.printStackTrace();
                    }
                    result= "streamfetched";
            }
            //fetch data
            else
            {
                try {
                    URL url = new URL(fetch_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);//get response from server
                    //To get Response from server
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    response = stringBuilder.toString();
                    httpURLConnection.disconnect();
                    Log.e("RESPONSETAG", response);
                    // return response;
                } catch (MalformedURLException e) {
                     e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray JA = new JSONArray(response);
                    JSONObject json = null;
                    int i;
                    name = new String[JA.length()];
                    for (i = 0; i < JA.length(); i++) {
                        json = JA.getJSONObject(i);
                        name[i] = json.getString("Stream");
                        // Toast.makeText(this,"ssss",Toast.LENGTH_SHORT).show();
                    }

                    for (i = 0; i < name.length; i++) {
                        list.add(name[i]);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                result= "add";

            }
            return  result;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

        @Override
        protected void onPostExecute(String result) {

        if(result.equals("Insertion successfull"))
        {
            streamTxt.setText("");
            list.add(stream);
            hs.addAll(list);
            list.clear();
            list.addAll(hs);
            hs.clear();// list.add(stream);
            dataAdapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
            sp.setAdapter(dataAdapter1);

        }
        else if(result.equals("Insertion sem successfull"))
        {
            semesterTxt.setText("");
            list2.add(semester);
            hs2.addAll(list2);
            list2.clear();
            list2.addAll(hs2);
            hs2.clear();// list.add(stream);
            dataAdapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list2);
            sem.setAdapter(dataAdapter1);
        }
        else if(result.equals("fetchsemupload"))
        {

            semset.addAll(semlist);
            semlist.clear();
            semlist.addAll(semset);
            semset.clear();
            dataAdapter2 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, semlist);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSem.setAdapter(dataAdapter2);
            //semlist.clear();
        }
        else if(result.equals("streamfetched"))
        {
            //semesterTxt.setText("");
            //list2.add(semester);
            streamset.addAll(streamlist);
            streamlist.clear();
            streamlist.addAll(streamset);
            streamset.clear();// list.add(stream);
            dataAdapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, streamlist);
            spinnerStream.setAdapter(dataAdapter1);
        }
        else if(result.equals("fetchsemester"))
        {
           // semesterTxt.setText("");
            hs2.addAll(list2);
            list2.clear();
            list2.addAll(hs2);
            hs2.clear();// list.add(stream);
            dataAdapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list2);
            sem.setAdapter(dataAdapter1);
        }
       else if(result.equals("hii"))
        {

        }
        else if(result.equals("downloadsuccess"))
        {

        }
        else
            {
                hs.addAll(list);
                list.clear();
                list.addAll(hs);
                hs.clear();
            dataAdapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
            sp.setAdapter(dataAdapter1);
        }

    }

}
