package com.example.justmark.Student;

public class Helper {
    String name,id,email,colleg,dept,b,y,sm,s;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    String pass;
    public Helper(String name, String id, String email, String colleg,String pass, String dept, String b, String y, String sm, String s) {
        this.name = name;
        this.id = id;
        this.pass=pass;
        this.email = email;
        this.colleg = colleg;
        this.dept = dept;
        this.b = b;
        this.y = y;
        this.sm = sm;
        this.s = s;
    }



    Helper(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getColleg() {
        return colleg;
    }

    public void setColleg(String colleg) {
        this.colleg = colleg;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }


}
