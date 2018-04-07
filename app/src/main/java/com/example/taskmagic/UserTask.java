package com.example.taskmagic;

import android.graphics.Bitmap;
import android.net.Uri;
import java.text.SimpleDateFormat;
import android.location.Location;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Yipu on 21/02/2018.
 */

public class UserTask implements Serializable {
    private String id;
    private String title;
    private String description;
    private String status = "Requested";
    private boolean bidded = false;
    private boolean assigned = false;
    private boolean bidding = false;
    private boolean editing = false;
    private String requester;
    private String date;
    private String photoUriString;
    private String provider;
    private Double latitude;
    private Double longtitude;
    private String uri;
    private ArrayList<String> photoUris;
    private float lowestBid = -1f;

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
    }

    /**
     * This setter takes a String and changes date of this UserTask to the String
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @see setDescription()
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @see setStatus()
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * Takes a String and sets the status of this UserTask to the String
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @see setTitle()
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
     * @see setRequester()
     * @return
     */
    public String getRequester() {
        return requester;
    }

    /**
     * @see setPhotoUriString()
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
     * @see setId()
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
     * @see setDate()
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
     * @see setProvider()
     * @return
     */
    public String getProvider() {
        return provider;
    }

    /**
     * see setBidded
     * @return
     */
    public boolean isBidded() {
        return bidded;
    }

    /**
     * This setter takes a boolean and changes bidded of this UserTask to the String
     * @param bidded
     */
    public void setBidded(boolean bidded) {
        this.bidded = bidded;
    }

    /**
     * Returns a Boolean: determines if UserTask is Assigned
     * @return
     */
    public boolean isAssigned() {
        return assigned;
    }

    /**
     * This setter takes a boolean and changes assigned of this UserTask to the String
     * @param assigned
     */
    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    /**
     * Returns a Boolean: determines if UserTask is being bidded on
     * @return
     */
    public boolean isBidding() {
        return bidding;
    }

    /**
     * This setter takes a boolean and changes bidding of this UserTask to the String
     * @param bidding
     */
    public void setBidding(boolean bidding) {
        this.bidding = bidding;
    }

    /**
     * Returns a Boolean: determines if UserTask is being edited
     * @return
     */
    public boolean isEditing() {
        return editing;
    }

    /**
     * This setter takes a boolean and changes editing of this UserTask to the String
     * @param editing
     */
    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    /**
     * This setter takes a float and compare it with the current lowest bid of task, updates when necessary
     * @param amount
     */
    public void setLowestBid(float amount) {
        if (lowestBid < 0) {
            lowestBid = amount;
        } else if (lowestBid > amount) {
            this.lowestBid = amount;
        }
    }

    public float getLowestBid() {
        return lowestBid;
    }


    /**
     * see setLatitude()
     * @return
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Takes a Location and sets the latitude of this UserTask
     * @param latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }


    /**
     * see setLongtitude()
     * @return
     */
    public Double getLongtitude() {
        return longtitude;
    }

    /**
     * Takes a Location and sets the longtitude of this UserTask
     * @param longtitude
     */
    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }
}
