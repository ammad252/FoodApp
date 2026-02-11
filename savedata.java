package com.example.signupandlogin;

public class savedata {
  String  fname,lname,email,password,address,city,phoneno;
    public  savedata() {
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public savedata(String fname, String lname, String email, String password, String address, String city ,String phoneno) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.city = city;
        this.phoneno=phoneno;
    }
}
