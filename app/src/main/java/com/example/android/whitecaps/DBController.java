package com.example.android.whitecaps;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by Pramod on 25/05/17.
 */

public class DBController extends SQLiteOpenHelper {
    public static final String DB_NAME = "attendance.db";//DatabaseName
    public static  final  String TABLE_NAME ="teacher";
    public static  final  String COL1="Employee_id";
    public static  final  String COL2="Name";
    public static  final  String COL3="Initials";
    public static  final  String COL4="Email_Id";
    public static  final  String COL5="Contact_info";
    private Context context=null;
    private String DB_PATH = null;
    private SQLiteDatabase dataBase;
    public static final int DATABASE_VERSION = 10;


    public DBController(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        SQLiteDatabase database =this.getWritableDatabase();


    }



    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "create table " + TABLE_NAME +" (Employee_id TEXT PRIMARY KEY,Name TEXT,Initials TEXT,Email_Id TEXT,Contact_info TEXT)";
        database.execSQL(query);
    }

    //checking Database



    /**
     * Inserts User into SQLite DB
     * @param queryValues
     */
    public void insertUser(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Employee_id", queryValues.get("Employee_id"));
        values.put("Name", queryValues.get("Name"));
        values.put("Initials", queryValues.get("Initials"));
        values.put("Email_Id", queryValues.get("Email_id"));
        values.put("Contact_info", queryValues.get("Contact_info"));
        database.insert(DB_NAME, null, values);
        database.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        String query;
        query = "DROP TABLE IF EXISTS"+ TABLE_NAME;
        database.execSQL(query);
        onCreate(database);

    }
}