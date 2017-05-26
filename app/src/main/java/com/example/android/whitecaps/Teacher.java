package com.example.android.whitecaps;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 08-05-2017.
 */

public class Teacher implements Parcelable{

    private String teacherId;
    private String name;
    private String emailID;
    private String contactNo;
    private String teacherCode;
    private Context context;

    protected Teacher(Parcel in) {
        name = in.readString();
        emailID = in.readString();
        teacherCode = in.readString();
        contactNo = in.readString();
        teacherId = in.readString();
    }

    public static final Creator<Teacher> CREATOR = new Creator<Teacher>() {
        @Override
        public Teacher createFromParcel(Parcel in) {
            return new Teacher(in);
        }

        @Override
        public Teacher[] newArray(int size) {
            return new Teacher[size];
        }
    };

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

   // public void setName(String name) {
    //    this.name = name;
    //}

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

     public Teacher()
     {
         this.name="";
         this.emailID="";
         this.teacherCode="";
         this.contactNo="";
         this.teacherId="";
     }
    public Teacher(String name,String emailID, String teacherCode,String contactNo,String teacherId)
    {
        this.name=name;
        this.emailID=emailID;
        this.teacherCode=teacherCode;
        this.contactNo=contactNo;
        this.teacherId=teacherId;
    }



    public Teacher(Context context) {
        this.context = context;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getName());
        dest.writeString(getEmailID());
        dest.writeString(getTeacherCode());
        dest.writeString(getContactNo());
        dest.writeString(getTeacherId());

    }
}