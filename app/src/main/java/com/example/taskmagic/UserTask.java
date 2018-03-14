package com.example.taskmagic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Yipu on 21/02/2018.
 */

public class UserTask {
    private String id;
    private String title;
    private String description;
    private String status="Requested";
    private String requester;
    private String date;
    private Photo photo;
    private BidList bids;

    public UserTask(){

    }

    //new UserTask(whatIsID, newTitle, newDescription, taskRequester, defaultStatus, photos, bids);

    public UserTask(String taskID, String title, String description, String userID, Photo photo, BidList bids) {
        this.id = taskID;
        this.title = title;
        this.description = description;
        this.requester = userID;
        this.photo = photo;
        this.bids = bids;
        // https://stackoverflow.com/questions/8654990/how-can-i-get-current-date-in-android -> 2018-Mar-13
        this.date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequester() {
        return requester;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }
}
