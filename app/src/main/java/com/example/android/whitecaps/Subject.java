package com.example.android.whitecaps;

/**
 * Created by User on 08-05-2017.
 */

public class Subject {
    private String subCode;
    private String subName;
    private String tCode;
    private String tName;

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }
    // DataMgr d = new DataMgr();

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

}
