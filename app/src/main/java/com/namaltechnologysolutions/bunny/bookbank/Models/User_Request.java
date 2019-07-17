package com.namaltechnologysolutions.bunny.bookbank.Models;

/**
 * Created by Bunny on 12/22/2018.
 */

public class User_Request {

    private String profile_Image_Url;
    private String name;
    private String contact_No;
    private String city;
    private String address;
    private String request;
    private String current_Date;

    public User_Request(String Name, String Contact_No, String Address, String City, String Request,String Current_Date,String Profile_Image_Url) {

        this.name = Name;
        this.contact_No = Contact_No;
        this.address = Address;
        this.city = City;
        this.request= Request;
        this.current_Date=Current_Date;
        this.profile_Image_Url = Profile_Image_Url;
    }

    public User_Request() {
    }

    public String getProfile_Image_Url() {
        return profile_Image_Url;
    }

    public void setProfile_Image_Url(String profile_Image_Url) {
        this.profile_Image_Url = profile_Image_Url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_No() {
        return contact_No;
    }

    public void setContact_No(String contact_No) {
        this.contact_No = contact_No;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getCurrent_Date() {
        return current_Date;
    }

    public void setCurrent_Date(String current_Date) {
        this.current_Date = current_Date;
    }

    @Override
    public String toString() {
        return "User_Request{" +
                "Name='" + name + '\'' +
                ", Contact No='" + contact_No + '\'' +
                ", Address='" + address + '\'' +
                ", City='" + city + '\'' +
                ", Description='" + request + '\'' +
                ", Current Date='" + current_Date + '\'' +
                '}';
    }
}
