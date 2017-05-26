package com.example.android.whitecaps;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 08-05-2017.
 */

public class Student implements Parcelable{
    private String studEnrollID;
    private String universityRollNo;
    private String classRollNo;
    private String name;
    private String emailID;
    private String contactNo;
    private String stream;
    private String semester;
    private String section;

    protected Student(Parcel in) {
        studEnrollID = in.readString();
        universityRollNo = in.readString();
        classRollNo = in.readString();
        name = in.readString();
        emailID = in.readString();
        contactNo = in.readString();
         stream = in.readString();
         semester = in.readString();
         section = in.readString();
    }

    public Student(String  studEnrollID,String  universityRollNo, String  classRollNo,String  name,
                   String emailID,String contactNo,String stream,String semester,String section)
    {
        this.studEnrollID=studEnrollID;
        this.universityRollNo=universityRollNo;
        this.classRollNo=classRollNo;
        this.name=name;
        this.emailID=emailID;
        this.contactNo=contactNo;
        this.stream=stream;
        this.semester=semester;
        this.section=section;

    }
    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getStudEnrollID() {
        return studEnrollID;
    }

    public void setStudEnrollID(String studEnrollID) {
        this.studEnrollID = studEnrollID;
    }

    public String getUniversityRollNo() {
        return universityRollNo;
    }

    public void setUniversityRollNo(String universityRollNo) {
        this.universityRollNo = universityRollNo;
    }

    public String getClassRollNo() {
        return classRollNo;
    }

    public void setClassRollNo(String classRollNo) {
        this.classRollNo = classRollNo;
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

   public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(studEnrollID);
        dest.writeString(universityRollNo);
        dest.writeString(classRollNo);
        dest.writeString(name);
        dest.writeString(emailID);
        dest.writeString(contactNo);
        dest.writeString(stream);
        dest.writeString(semester);
        dest.writeString(section);
    }
}
