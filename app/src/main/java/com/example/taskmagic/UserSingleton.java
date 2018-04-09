package com.example.taskmagic;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hyusuf on 2018-03-10.
 */

public class UserSingleton {
    private final static UserSingleton obj=new UserSingleton();
    private FirebaseAuth mAuth;
    private String userName;
    private UserSingleton(){}
    private GoogleApiClient mGoogleApiClient;


    public static UserSingleton getInstance(){
        return obj;
    }

    public void setAuth(FirebaseAuth auth){
        this.mAuth=auth;
    }

    public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
        this.mGoogleApiClient = mGoogleApiClient;
    }

    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public FirebaseAuth getmAuth(){
        return mAuth;
    }
    public String getUserId(){
        return mAuth.getCurrentUser().getUid();
    }
    public void logout() {
        mGoogleApiClient.connect();
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                mAuth.signOut();
                if(mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            Log.d("onions", "onResult: ");
                        }
                    });
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                Log.d("yo", "Google API Client Connection Suspended");
            }
        });
    }
}