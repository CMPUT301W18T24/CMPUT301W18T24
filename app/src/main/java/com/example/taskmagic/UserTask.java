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
    private Boolean bidded;
    private Boolean assigned;
    private String requester;
    private String date;
    private String photoUriString;
    private String provider;
    private Location location;
    private String uri;
    private ArrayList<String> photoUris;

    public UserTask(){

    }

    /**
     * Constructor creates a new UserTask object using parameters
     * @param title
     * @param description
     * @param userID
     * @param photo
     */
    public UserTask(String title, String description, String userID, String photo) {
        this.id = "-1";
        this.title = title;
        this.description = description;
        this.requester = userID;
        this.uri=photo;
        this.provider="";
        this.photoUriString = photo; // if this is anything like BidsList, photo can just contain owner ID which can be retrieved *******
        // https://stackoverflow.com/questions/8654990/how-can-i-get-current-date-in-android -> 2018-Mar-13
        this.date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        this.bidded=false;
        this.assigned=false;
    }

    /**
     * This setter takes a String and changes date of this UserTask to the String
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @see
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @see
     * @return
     */
    public String getStatus() {
        return status;
    }

    public Boolean getAssigned() {
        return assigned;
    }

    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }

    /**
     * Takes a String and sets the status of this UserTask to the String
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getBidded() {
        return bidded;
    }

    public void setBidded(Boolean bidded) {
        this.bidded = bidded;
    }

    /**
     * @see
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Takes a string and sets the title of this UserTask to this String
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
    * Takes a String and changes the description of this UserTask to the String
    * @param description
     **/
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @see
     * @return
     */
    public String getRequester() {
        return requester;
    }

    /**
     * @see
     * @return
     */
    public String getPhotoUriString() {
        return photoUriString;
    }

    /**
     * Takes a string and sets the photoUriString of this UserTask to the String
     * @param photoUri
     */
    public void setPhotoUriString(String photoUri) {
        this.photoUriString = photoUri;
    }

    /**
     * Takes a string and sets the requester of this UserTask to the String
     * @param requester
     */
    public void setRequester(String requester) {
        this.requester = requester;
    }

    /**
     * @see
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Takes a string and sets the id of this UserTask to the String
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @see
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns a Boolean: determines if UserTask is editable
     * @return
     */
    public boolean allowEditing() {
        return (!bidded & !assigned);
    }

    /**
     * Returns a Boolean: determines if UserTask is biddable
     * @return
     */
    public boolean allowBidding() {
        return !assigned;
    }

    /**
     * Takes a string and sets the provider of this UserTask to the String
     * @param provider
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     * @see
     * @return
     */
    public String getProvider() {
        return provider;
    }

    /**
     * Takes a Location and sets the Location of this UserTask to the Location
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @see
     * @return
     */
    public Location getLocation() {
        return location;
    }
}
