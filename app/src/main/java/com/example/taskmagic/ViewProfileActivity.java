/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import misc.BottomNavigationViewHelper;

/**
 * Created by steve on 2018-03-11.
 * This activity shows details of the current logged in user
 * The current logged in user can modify his own profile on this activity
 */

public class ViewProfileActivity extends AppCompatActivity {

    private UserTask task;
    private FireBaseManager fmanager;
    private DatabaseReference db;
    private FirebaseAuth auth;

    private BottomNavigationView mProfileNav;
    private FrameLayout mProfileFrame;

    private BidFrag bidFragment;
    private AssignedFrag assignFragment;
    private UserProfileFrag userFragment;
    private RequestedFrag requestFragment;
    private Messages_Frag messagesFrag;
    private ImageView image;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile);
        mProfileNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(mProfileNav);
        mProfileFrame = (FrameLayout) findViewById(R.id.user_fragment_container);
        bidFragment = new BidFrag();
        assignFragment = new AssignedFrag();
        userFragment = new UserProfileFrag();
        requestFragment = new RequestedFrag();
        messagesFrag=new Messages_Frag();
        image=(ImageView)findViewById(R.id.profileImage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mbuilder= new AlertDialog.Builder(getApplicationContext());
                View mView=getLayoutInflater().inflate(R.layout.dialog_selectphoto,null);
            }
        });

        setFragment(userFragment);


        mProfileNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.my_info:
                                setFragment(userFragment);
                                return true;
                            case R.id.messages:
                                setFragment(messagesFrag);
                                return true;
                            case R.id.requested_task:
                                setFragment(requestFragment);
                                return true;
                            case R.id.assigned_task:
                                setFragment(assignFragment);
                                return true;
                            case R.id.bidded_task:
                                setFragment(bidFragment);
                                return true;

                            default: return false;
                        }
                    }

                });
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.user_fragment_container, fragment);
        transaction.commit();
    }

}