package com.example.taskmagic;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by steve on 2018-03-11.
 */

public class ViewProfileActivity extends AppCompatActivity {

    private UserTask task;
    private FireBaseManager fmanager;
    private DatabaseReference db;
    private FirebaseAuth auth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homefeed);
        UserSingleton singleton = UserSingleton.getInstance();
        auth = singleton.getmAuth();
        fmanager = new FireBaseManager(singleton.getmAuth(), getApplicationContext());

        user_profile_frag user_frag = new user_profile_frag();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.user_fragment_container, user_frag);
        transaction.commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.my_info:
                                user_profile_frag user_frag = new user_profile_frag();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.user_fragment_container, user_frag);
                                transaction.commit();
                                break;
                            case R.id.requested_task:
                                requested_frag req_frag = new requested_frag();
                                FragmentManager fragmentManager1 = getFragmentManager();
                                FragmentTransaction transaction1 = fragmentManager1.beginTransaction();
                                transaction1.replace(R.id.user_fragment_container, req_frag);
                                transaction1.commit();
                                break;
                            case R.id.assigned_task:
                                assigned_frag ass_frag = new assigned_frag();
                                FragmentManager fragmentManager2 = getFragmentManager();
                                FragmentTransaction transaction2 = fragmentManager2.beginTransaction();
                                transaction2.replace(R.id.user_fragment_container, ass_frag);
                                break;
                            case R.id.bidded_task:
                                bids_frag bid_frag = new bids_frag();
                                FragmentManager fragmentManager3 = getFragmentManager();
                                FragmentTransaction transaction3 = fragmentManager3.beginTransaction();
                                transaction3.replace(R.id.user_fragment_container, bid_frag);
                                break;
                        }
                        return true;
                    }
                });
    }

}