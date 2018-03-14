package com.example.taskmagic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Yipu on 21/02/2018.
 */

public class UserTask implements Serializable{
    private String id;
    private String title;
    private String description;
    private Boolean bidded = false;
    private Boolean assigned = false;
    private String requester;
    private Photo photo;
    private String date = "2018-03-01";

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UserTask(){

    }

    public UserTask(String title, String description, String userid) {
        this.title = title;
        this.description = description;
        this.requester=userid;
    }

    public String getDescription() {
        return description;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

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

    public boolean allowEditing() {
        return (!bidded & !assigned);
    }

    public boolean allowBidding() {
        return !assigned;
    }
}
