package com.example.taskmagic;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.FrameLayout;
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

    private BottomNavigationView mProfileNav;
    private FrameLayout mProfileFrame;
    private bids_frag bidFragment;
    private assigned_frag assignFragment;
    private user_profile_frag userFragment;
    private requested_frag requestFragment;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile);
        mProfileNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mProfileFrame = (FrameLayout) findViewById(R.id.user_fragment_container);
        bidFragment = new bids_frag();
        assignFragment = new assigned_frag();
        userFragment = new user_profile_frag();
        requestFragment = new requested_frag();

        setFragment(userFragment);

        mProfileNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.my_info:
                                setFragment(userFragment);
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