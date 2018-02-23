package com.example.taskmagic;

import java.util.ArrayList;

/**
 * Created by Yipu on 21/02/2018.
 */

class User {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String userName;
    private String password;
    private int phoneNumber;
    private Photo photo;
    private ArrayList<Task> myTasks = new ArrayList<Task>();
    private ArrayList<Bid> myBids = new ArrayList<Bid>();

    public User(String firstName, String lastName, String emailAddress, String userName, String password, int phoneNumber, Photo photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
        this.myBids = new ArrayList<Bid>();
        this.myTasks = new ArrayList<Task>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public ArrayList<Bid> getBidsList() {
        return myBids;
    }

    public ArrayList<Task> getTasksList() {
        return myTasks;
    }

    public void addTask(Task task) {
        myTasks.add(task);
    }

    public int getTasksSize() {
        return myTasks.size();
    }

    //Removes a task from user's task list
    public void removeTask(int taskID) {
    }

}
