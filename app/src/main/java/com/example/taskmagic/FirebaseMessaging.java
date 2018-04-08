/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hyusuf on 2018-03-28.
 */

public class FirebaseMessaging extends FirebaseMessagingService {
    private static final String TAG = "FireBase Messaging";
    private static final String data_type = "data_type";
    private static final String data_type_bid = "bid";
    private static final String data_type_chat = "chat";
    private static final String data_title = "taskTitle";
    private static final String data_message = "body";
    private static final String data_sender = "senderId";
    private static final String data_taskId = "id";
    private static final String data_receiver = "receiverId";
    private UserSingleton singleton;
    private Intent intent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            String identifyDataType = remoteMessage.getData().get(data_type);
            if (identifyDataType.equals(data_type_bid)) {
                String message = remoteMessage.getData().get(data_message);
                String sender= remoteMessage.getData().get(data_sender);
                String receiver=remoteMessage.getData().get(data_receiver);
                String taskid= remoteMessage.getData().get(data_taskId);
                String title= remoteMessage.getData().get(data_title);
                sendBidNotification(message,sender,receiver,taskid,title);
            } else if (identifyDataType.equals(data_type_chat) && (!isAppInForeground(this,getPackageName()))){
                String message = remoteMessage.getData().get(data_message);
                String receiver= remoteMessage.getData().get(data_receiver);
                Log.d(TAG, "onMessageReceived: " + message);
                chatNotification(message,receiver);
            }
        }catch(NullPointerException e){

        }
        Log.d(TAG, "onMessageReceived: " + remoteMessage.getData());

    }
    // check if this works
    private void chatNotification(String message,String receiver) {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra("id",receiver);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("TaskMagic")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());


    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    public void sendBidNotification(String message,String sender,String receiverId,String taskid,String taskTitle) {
        Log.d(TAG, "sendBidNotification: building bid Notification");
        if (!isAppInForeground(this,getPackageName())){
            intent = new Intent(this, MainActivity.class);
        }
        else {
            intent = new Intent(this, NotificationActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("TaskMagic")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
        NotificationMSG nMessage= new NotificationMSG(sender,receiverId,message,taskid,taskTitle);
        saveNotification(nMessage);
    }

    //https://stackoverflow.com/questions/41735755/in-android-how-to-prevent-firebase-push-notification-when-app-is-in-foreground
    private boolean isAppInForeground(Context context, String packageName){
        String[] activePackages;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activePackages = getActivePackages(am);
        try {
            if (activePackages != null) {
                return activePackages[0].equals(packageName);
            }
        }catch(ArrayIndexOutOfBoundsException e){

        }
        return false;
    }
    //https://stackoverflow.com/questions/41735755/in-android-how-to-prevent-firebase-push-notification-when-app-is-in-foreground
    private String[] getActivePackages(ActivityManager mActivityManager) {
        final Set<String> activePackages = new HashSet<>();
        final List<ActivityManager.RunningAppProcessInfo> processInfos = mActivityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                activePackages.addAll(Arrays.asList(processInfo.pkgList));
            }
        }
        return activePackages.toArray(new String[activePackages.size()]);
    }

    public void saveNotification(NotificationMSG notificationMSG) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("Notifications").child(notificationMSG.getReceiverId()).setValue(notificationMSG).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: " + "Notification sucessfully saved");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                  Log.d(TAG, "onFailure: " + "Failed to save notification");
            }
        });
    }

}

