package com.example.taskmagic;

import android.content.Intent;
import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private Button addTaskButton;
    private Button myTasksButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homefeed);
        UserSingleton singleton = UserSingleton.getInstance();
        auth = singleton.getmAuth();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Log.d("homefeed", "onCreate: " + singleton.getmAuth() + db);

        fmanager = new FireBaseManager(singleton.getmAuth(), getApplicationContext());
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