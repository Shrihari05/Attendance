package com.example.justmark.Admin;

import java.util.ArrayList;

public class subject {
    String name;
    public subject(){

    }


ArrayList<String> stuid;
    String dept;
    String batch;
    String col;
    String fac;
    String cnt;

    public int getTh() {
        return th;
    }

    public void setTh(int th) {
        this.th = th;
    }

    int th;
    public subject(String name, String dept, String batch, String col, String fac, String cnt,int th) {
        this.name = name;
this.th=th;
        this.dept = dept;
        this.batch = batch;
        this.col = col;
        this.fac = fac;
        this.cnt = cnt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getStuid() {
        return stuid;
    }

    public void setStuid(ArrayList<String> stuid) {
        this.stuid = stuid;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getFac() {
        return fac;
    }

    public void setFac(String fac) {
        this.fac = fac;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }
}
