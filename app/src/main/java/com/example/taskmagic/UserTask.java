package com.example.taskmagic;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Yipu on 21/02/2018.
 */

public class UserTask implements Serializable {
    private String id;
    private String title;
    private String description;
    private String status="Requested";
    private String requester;
    private String provider;
    private Location location;
    private String uri;
    private ArrayList<String> photoUris;

    public UserTask(){

    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<String> getPhotoUris() {
        return photoUris;
    }
    public void addUri(String Uri){
        photoUris.add(Uri);
    }
    public void editUri(String old_uri,String newUri){
        for(int i=0;i<photoUris.size();i++){
            String uri= photoUris.get(i);
            if (uri==old_uri){
                photoUris.set(i,newUri);
            }
        }
    }
    public void deleteUri(String old_uri){
        for(int i=0;i<photoUris.size();i++){
            String uri= photoUris.get(i);
            if (uri==old_uri){
                photoUris.remove(i);
            }
        }
    }

    public UserTask(String title, String description, String userid,String photo) {
        this.title = title;
        this.description = description;
        this.requester=userid;
        this.uri=photo;
        this.provider=null;
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


    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
