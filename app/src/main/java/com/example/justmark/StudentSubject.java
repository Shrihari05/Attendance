package com.example.justmark;

public class StudentSubject {
    String name;
public StudentSubject(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStupre() {
        return stupre;
    }

    public void setStupre(int stupre) {
        this.stupre = stupre;
    }

    int stupre;
    public StudentSubject(String name,int stupre){
this.name=name;
this.stupre=stupre;
    }
}
