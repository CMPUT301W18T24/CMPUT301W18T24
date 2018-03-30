package com.example.taskmagic;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

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
    private Bid_frag bidFragment;
    private assigned_frag assignFragment;
    private User_profile_frag userFragment;
    private Requested_frag requestFragment;
    private ImageView image;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile);
        mProfileNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mProfileFrame = (FrameLayout) findViewById(R.id.user_fragment_container);
        bidFragment = new Bid_frag();
        assignFragment = new assigned_frag();
        userFragment = new User_profile_frag();
        requestFragment = new Requested_frag();
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