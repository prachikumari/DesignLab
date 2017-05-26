package com.example.android.whitecaps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

//Login Page
public class LoginScreen extends AppCompatActivity implements LoginView{

    private static EditText username;
    private static EditText password;
    private static Button login_btn;
    private static String email;
    private static String table;
    // DigitalAttendanceMgr dm;
    int i;

    //Getter Setter

    public static void setTable(String table) {
        LoginScreen.table = table;
    }
    static public String getEmail() {return email;}
    LoginMgr lm;
    LoginService service = new LoginService();
    SessionManager session;

    LoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {//execution starts here

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(getApplicationContext());

        username = (EditText)findViewById(R.id.editText_user);
        password = (EditText)findViewById(R.id.editText_password);
        login_btn = (Button)findViewById(R.id.button_login);
        presenter =  new LoginPresenter(this,service);

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();

        Intent intent=getIntent();
        String str = intent.getStringExtra("mystr");//getting value from mainPage(i.e 1/2/3)
        if(str == null) str = 1 +"" ;
        i = Integer.parseInt(str);
        //viewData();
        //set table according to value
        if(i==1) table="student";
        if(i==2) table="teacher";
        if(i==3) table="admin";
        //table name is set as per value
        LoginButton(table);//method called
    }




    //showsalert with message
    public static void showalert(String msg, Context context)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(msg);
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // return;
            }
        });
        alertDialog.show();
    }
    public  void LoginButton(final String table) {


        login_btn.setOnClickListener(//on clicking login button
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lm = new LoginMgr();
                        lm.setEmailid(username.getText().toString());
                        lm.setPassword(password.getText().toString());
                        lm.setTable(table);
                        lm.login(LoginScreen.this);  //method called to verify user
                    }
                }
        );
    }

    @Override
    public String getUsername() {
        return username.getText().toString();
    }

    @Override
    public void showUsernameError(int resId) {
        username.setError(getString(resId));
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public void showPasswordError(int resId) {
        password.setError(getString(resId));
    }

    @Override
    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }

    @Override
    public void showLoginError(int resId) {
        Toast.makeText(this, getString(resId), LENGTH_SHORT).show();
    }
}