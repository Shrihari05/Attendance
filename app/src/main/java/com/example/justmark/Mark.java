package com.example.justmark;

public class Mark {
    public Mark(String roll, String lat, String longt, String sub, String dat,String bat) {
        this.roll = roll;
        this.lat = lat;
        this.longt = longt;
        this.sub = sub;
        this.dat = dat;
        this.bat = bat;

    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongt() {
        return longt;
    }

    public void setLongt(String longt) {
        this.longt = longt;
    }

    String roll,lat,longt;
    String sub;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getBat() {
        return bat;
    }

    public void setBat(String bat) {
        this.bat = bat;
    }

    String bat;
    String dat;
}
