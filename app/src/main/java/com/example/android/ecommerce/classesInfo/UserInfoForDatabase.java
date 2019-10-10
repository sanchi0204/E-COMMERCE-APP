package com.example.android.ecommerce.classesInfo;

public class UserInfoForDatabase {

    private String Name;
    private String Phone;
    private String Address;
    private String City;
    private String State;
    private String Email;
    private String Website;
    private String Instagram;
    private String Facebook;
    private String LinkedIn;
    private String Designation;
    private String Worktype;
    private String PicUri;

    public UserInfoForDatabase() {
    }

    public UserInfoForDatabase(String name, String phone, String address, String city, String state, String email, String website, String instagram, String facebook, String linkedIn, String designation, String worktype, String picUri) {
        Name = name;
        Phone = phone;
        Address = address;
        City = city;
        State = state;
        Email = email;
        Website = website;
        Instagram = instagram;
        Facebook = facebook;
        LinkedIn = linkedIn;
        Designation = designation;
        Worktype = worktype;
        PicUri = picUri;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getInstagram() {
        return Instagram;
    }

    public void setInstagram(String instagram) {
        Instagram = instagram;
    }

    public String getFacebook() {
        return Facebook;
    }

    public void setFacebook(String facebook) {
        Facebook = facebook;
    }

    public String getLinkedIn() {
        return LinkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        LinkedIn = linkedIn;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getWorktype() {
        return Worktype;
    }

    public void setWorktype(String worktype) {
        Worktype = worktype;
    }

    public String getPicUri() {
        return PicUri;
    }

    public void setPicUri(String picUri) {
        PicUri = picUri;
    }
}
