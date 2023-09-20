package com.example.justmark.Faculty;

public class HelperFac {

    String name, email, dept, password,user,col;

    public String getName() {
        return name;
    }
    public String getCollege() {
        return col;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setCollege(String col) {
        this.col = col;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HelperFac(String name, String email, String user, String dept, String col, String password) {
        this.name = name;
        this.email = email;
        this.user=user;
        this.dept = dept;
        this.password = password;
        this.col=col;
    }

    public HelperFac() {
    }
}