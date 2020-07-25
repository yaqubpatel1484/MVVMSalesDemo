package com.myproject.salesdemomvvm.login;

public class ExecutiveModel {

    private String tid,name ,contact, address, designation,hire_date,photo,username,password,email,token,region;


    public ExecutiveModel(String tid, String name, String contact, String address, String designation, String hire_date, String photo, String username, String password, String email, String token, String region) {
        this.tid = tid;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.designation = designation;
        this.hire_date = hire_date;
        this.photo = photo;
        this.username = username;
        this.password = password;
        this.email = email;
        this.token = token;
        this.region = region;
    }

    public String getTid() {
        return tid;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getDesignation() {
        return designation;
    }

    public String getHire_date() {
        return hire_date;
    }

    public String getPhoto() {
        return photo;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getRegion() {
        return region;
    }
}
