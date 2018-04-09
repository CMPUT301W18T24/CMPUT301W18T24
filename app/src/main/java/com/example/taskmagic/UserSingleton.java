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

/**
 * This is used to hold the contents of the authenticated user
 *
 */
public class UserSingleton {
    private final static UserSingleton obj = new UserSingleton();
    private FirebaseAuth mAuth;
    private String userName;
    private GoogleApiClient mGoogleApiClient;

    private UserSingleton() {
    }


    /**
     *
     * @return UserSingleton obj
     */
    public static UserSingleton getInstance() {
        return obj;
    }

    /**
     *
     * @param auth
     */
    public void setAuth(FirebaseAuth auth) {
        this.mAuth = auth;
    }

    /**
     *
     * @param mGoogleApiClient
     */
    public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
        this.mGoogleApiClient = mGoogleApiClient;
    }

    /**
     *
     * @return mGooogleAPiClient
     */
    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    /**
     *
     * @return uesrName
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return auth
     */
    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    /**
     *
     * @return userAuth
     */
    public String getUserId() {
        return mAuth.getCurrentUser().getUid();
    }

    /**
     * logs out a user
     */
    public void logout() {
        mGoogleApiClient.connect();
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                mAuth.signOut();
                if (mGoogleApiClient.isConnected()) {
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
