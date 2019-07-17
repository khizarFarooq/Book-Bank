package com.namaltechnologysolutions.bunny.bookbank.Models;

import android.widget.ImageView;

/**
 * Created by Bunny on 12/21/2018.
 */

public class other_Request_Model_Class {
    private String currentUserProfileUrl,userName,phoneNo,date,home,location,userRequest;

    public String getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(String userRequest) {
        this.userRequest = userRequest;
    }

    public other_Request_Model_Class(String currentUserProfileUrl, String userName, String phoneNo, String date, String home, String location, String userRequest) {
        this.currentUserProfileUrl=currentUserProfileUrl;
        this.userName = userName;
        this.phoneNo = phoneNo;
        this.date = date;
        this.home = home;
        this.location = location;
        this.userRequest = userRequest;

    }

    public other_Request_Model_Class() {
    }

    public String getUserName() {
        return userName;
    }

    public String getCurrentUserProfileUrl() {
        return currentUserProfileUrl;
    }

    public void setCurrentUserProfileUrl(String currentUserProfileUrl) {
        this.currentUserProfileUrl = currentUserProfileUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "other_Request_Model_Class{" +
                "currentUserProfileUrl='" + currentUserProfileUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", date='" + date + '\'' +
                ", home='" + home + '\'' +
                ", ic_location_1='" + location + '\'' +
                ", userRequest='" + userRequest + '\'' +
                ", phoneNo=" + phoneNo +
                '}';
    }
}
