package com.example.android.whitecaps;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 08-05-2017.
 */

public class Admin implements Parcelable {
    private String adminID;
    private String contactNo;
    private String address;
    private String name;
    private String emailID;
    private String password;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected Admin(Parcel in) {
        adminID = in.readString();
        contactNo=in.readString();
        address=in.readString();
        name = in.readString();
        emailID = in.readString();
        contactNo = in.readString();
        password=in.readString();

    }

    public Admin(String adminID,String contactNo,String address,String name, String emailID,String password)
    {
        this.adminID=adminID;
        this.contactNo=contactNo;
        this.address=address;
        this.name=name;
        this.emailID=emailID;
        this.password=password;


    }
    public static final Creator<Admin> CREATOR = new Creator<Admin>() {
        @Override
        public Admin createFromParcel(Parcel in) {
            return new Admin(in);
        }

        @Override
        public Admin[] newArray(int size) {
            return new Admin[size];
        }
    };

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(adminID);
        dest.writeString(contactNo);
        dest.writeString(address);
        dest.writeString(name);
        dest.writeString(emailID);
        dest.writeString(password);
    }
}
