package com.example.taskmagic;

import android.graphics.Bitmap;
import android.net.Uri;

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
    private String photoUriString;

    public UserTask(){

    }

    //new UserTask(whatIsID, newTitle, newDescription, taskRequester, defaultStatus, photos, bids);

    public UserTask(String title, String description, String userID, String photo) {
        this.id = "-1";
        this.title = title;
        this.description = description;
        this.requester = userID;
        this.photoUriString = photo; // if this is anything like BidsList, photo can just contain owner ID which can be retrieved *******
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

    public String getPhotoUriString() {
        return photoUriString;
    }

    public void setPhotoUriString(String photoUri) {
        this.photoUriString = photoUri;
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
