/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;

/**
 * Created by hyusuf on 2018-03-26.
 */

public class FireBaseIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken= FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshedToken );
    }
    private void sendRegistrationToServer(String Token){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        UserSingleton singleton= UserSingleton.getInstance();
        reference.child("users").child(singleton.getUserId()).child("token").setValue(Token);
    }
}
