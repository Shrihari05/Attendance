package com.example.justmark.Admin;


import java.util.ArrayList;

public class Batch {
    String name;String year; String sem;



    ArrayList<String> sec;String dept;


    public Batch(String name, String year, String sem, String dept, ArrayList<String> sec){
        this.name=name;
        this.sem=sem;
        this.year=year;
        this.sec=sec;
this.dept=dept;
    }

    public Batch(){

    }

    public ArrayList<String> getSec() {
        return sec;
    }
    public void setSec(ArrayList<String> sec) {
        this.sec = sec;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getSem(){
        return sem;
    }
    public void setSem(String sem){
        this.sem=sem;
    }

    public String getYear(){
        return year;
    }
    public void setYear(String year){
        this.year=year;
    }

    public String getDept(){
        return dept;
    }
    public void setDept(String dept){
        this.dept=dept;
    }



}

