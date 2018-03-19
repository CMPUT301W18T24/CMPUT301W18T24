package com.example.taskmagic;

import android.graphics.Bitmap;
import android.net.Uri;
import java.text.SimpleDateFormat;
import android.location.Location;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Yipu on 21/02/2018.
 */

public class UserTask implements Serializable {
    private String id;
    private String title;
    private String description;
    private String status="Requested";
    private Boolean bidded = false;
    private Boolean assigned = false;
    private String requester;
    private String date;
    private String photoUriString;
    private String provider;
    private Location location;
    private String uri;
    private ArrayList<String> photoUris;

    //new UserTask(whatIsID, newTitle, newDescription, taskRequester, defaultStatus, photos, bids);

    public UserTask(String title, String description, String userID, String photo) {
        this.id = "-1";
        this.title = title;
        this.description = description;
        this.requester = userID;
        this.uri=photo;
        this.provider=null;
        this.photoUriString = photo; // if this is anything like BidsList, photo can just contain owner ID which can be retrieved *******
        // https://stackoverflow.com/questions/8654990/how-can-i-get-current-date-in-android -> 2018-Mar-13
        this.date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    public void setDate(String date) {
        this.date = date;
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

    public boolean allowEditing() {
        return (!bidded & !assigned);
    }

    public boolean allowBidding() {
        return !assigned;
    }
}
