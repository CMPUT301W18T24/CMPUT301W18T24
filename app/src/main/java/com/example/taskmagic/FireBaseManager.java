package com.example.taskmagic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.*;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by hyusuf on 2018-03-08.
 */

public class FireBaseManager implements onGetMyTaskListener,OnGetUserInfoListener {
    private FirebaseAuth mAuth;
    private DatabaseReference database;

    private StorageReference storage;

    private String taskTag = "task";
    private String userTag = "users";
    private String bidTag="bids";
    private Context context;
    private FirebaseAuth.AuthStateListener listener;
    private static UserTask task;

    public FireBaseManager(FirebaseAuth user, DatabaseReference db, Context context) {
        this.mAuth = user;
        this.database = db;
        this.context = context;
        this.task=new UserTask();

    }

    @Override
    public void onSuccess(TaskList taskList) {

    }

    @Override
    public void onSuccess(User user) {

    }

    @Override
    public void onFailure(String message) {

    }


    //successfully adds a Task to FireBase Database under user login
    public void addTask(UserTask task) {
        DatabaseReference mRef=database.child(taskTag).child(task.getRequester());
        String taskId=mRef.push().getKey();
        task.setId(taskId);
        mRef.child(taskId).setValue(task).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Profile Successfully Saved", Toast.LENGTH_LONG).show();
            }
        });
    }

    //can be used for edit profile
    public void saveProfile(User user) {
        database.child(userTag).child(user.getId()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(@NonNull Void T) {
                //Do whatever
                Toast.makeText(context, "Profile Successfully Saved", Toast.LENGTH_LONG).show();
            }
        });
    }
    // retrieves userinfo from database
    public void getUserInfo(final String userid ,final OnGetUserInfoListener listener) {
        database.child(userTag).child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               User user=dataSnapshot.getValue(User.class);
               listener.onSuccess(user);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure(databaseError.toString());
            }
        });
    }
    //public void getSpecificTaskData(final taskid)



    //This function asychronously retrieves logged in user Tasks from the Database
    public void getMyTaskData(final String requestor, final onGetMyTaskListener listener) {
        final TaskList taskList = new TaskList();
        database.child(taskTag).child(requestor).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    UserTask task=dataSnapshot.getValue(UserTask.class);
                    taskList.add(task);
                }
                listener.onSuccess(taskList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure(databaseError.toString());
            }
        });
    }
    // successfully adds a bid to the database
    public void addBid(final Bid bid){
        database.child(bidTag).child(bid.getTaskID()).setValue(bid).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Bid Successfully Saved", Toast.LENGTH_LONG).show();
            }
        });

    }



}








