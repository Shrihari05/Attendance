package com.example.justmark.Faculty;

public class Attendance {
    public Attendance(){}
    public Attendance(String bat, String dat, String lat, String longi, String val,int pre,int con) {
        this.bat = bat;
        this.dat = dat;
        this.lat = lat;
        this.pre=pre;
        this.longi = longi;
        this.val = val;
        this.con=con;
    }

    String bat;

    public int getCon() {
        return con;
    }

    public void setCon(int con) {
        this.con = con;
    }

    int con;
int pre;
    public int getPre() {
        return pre;
    }

    public void setPre(int pre) {
        this.pre = pre;
    }

    public String getBat() {
        return bat;
    }

    public void setBat(String bat) {
        this.bat = bat;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    String dat;
    String lat;
    String longi;
    String val;
}
