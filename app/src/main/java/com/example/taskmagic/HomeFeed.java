package com.example.taskmagic;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hyusuf on 2018-03-08.
 */

public class HomeFeed extends AppCompatActivity {
    private UserTask task;
    private FireBaseManager fmanager;
    private DatabaseReference db;
    private FirebaseAuth auth;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ActionBar actionbar;
    private TextView textview;
    private LayoutParams layoutparams;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homefeed);
        UserSingleton singleton=UserSingleton.getInstance();
        auth=singleton.getmAuth();
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Log.d("homefeed", "onCreate: "+singleton.getmAuth()+ db);
        UserTask task=new UserTask("my computer","I need someone to fix it for me",auth.getUid());
        task.setId("-L7R_ajmSNl1Gf0j4r_7");
        fmanager=new FireBaseManager(singleton.getmAuth(),getApplicationContext());
        listener(singleton.getUserId());



    }


    private void listener(final String requestor) {
        fmanager.getRequestedTasks(requestor, new OnGetAllTaskReqListener() {
            @Override
            public void onSuccess(TaskList taskList) {
                updateView(taskList);
            }

            @Override
            public void onFailure(String message) {
                Log.d("listener", "onFailure: "+message);
            }
        });

    }

    public void updateView(TaskList taskList){
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter=new HomeFeedAdapter(taskList,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
