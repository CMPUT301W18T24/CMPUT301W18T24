package com.example.taskmagic;

import java.util.ArrayList;

/**
 * Created by Yipu on 21/02/2018.
 */

public class User {
    private String id;
    private String fullName;
    private String emailAddress;
    private String userName;
    private String password;
    private String phoneNumber;
    private Photo photo;


    public User(){
    }

    public User(String fullName, String emailAddress, String userName, String password, String phoneNumber, Photo photo) {
        this.fullName=fullName;
        this.emailAddress = emailAddress;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.photo = photo;

    }

    public User(String fullName, String emailAddress, String userName, String password, String phoneNumber) {
        this.fullName=fullName;
        this.emailAddress = emailAddress;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName(){
        return fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setFullName(String fullName) {
       this.fullName=fullName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }


}
