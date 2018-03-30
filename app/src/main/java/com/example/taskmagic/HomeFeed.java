package com.example.taskmagic;

import android.app.Activity;
import android.app.Notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

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
    private TextView textview;
    private BottomNavigationView mHomeNav;
    public static Boolean isActivity;
    private TaskList listUserTask;

    /**
     * this function sets up the home feed
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homefeed);
        final UserSingleton singleton = UserSingleton.getInstance();
        auth = singleton.getmAuth();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mHomeNav=(BottomNavigationView)findViewById(R.id.home_bottom_navigation);
        fmanager = new FireBaseManager(singleton.getmAuth(), getApplicationContext());
        listener(singleton.getUserId());
        mHomeNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.notifications:
                                startActivity(new Intent(HomeFeed.this, NotificationActivity.class));
                                return true;
                            case R.id.map:
                                startActivity(new Intent(HomeFeed.this, MapsActivity.class));
                                return true;
                            case R.id.addTask:
                                startActivity(new Intent(HomeFeed.this, CreateTaskActivity.class));
                                return true;
                            case R.id.search:
                                startActivity(new Intent(HomeFeed.this, SearchActivity.class));
                                return true;
                            case R.id.profile:
                                startActivity(new Intent(HomeFeed.this, ViewProfileActivity.class));
                                return true;
                            default: return false;
                        }
                    }

                });

        //https://medium.com/@harivigneshjayapalan/android-recyclerview-implementing-single-item-click-and-long-press-part-ii-b43ef8cb6ad8
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("click: ", "clicked" + position);
                UserTask chosenTask= listUserTask.getTask(position);
                Intent myIntent = new Intent(HomeFeed.this, ViewTaskActivity.class);
                myIntent.putExtra("UserTask",chosenTask);
                startActivity(myIntent);
            }
        }));

    }

    /**
     * this function listens to any changes to the datatbase and retrieves the info
     * @param requestor
     */
    private void listener(final String requestor) {
        fmanager.getRequestedTasks(requestor, new OnGetAllTaskReqListener() {
            @Override
            public void onSuccess(TaskList taskList) {
                Log.d("Succes", "onSuccess: "+taskList.getCount());
                TaskList list=new TaskList();
                list=taskList;
                listUserTask = taskList;
                updateView(taskList);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActivity=false;
    }

    /**
     * this function updates the recyclerview
     * @param taskList
     */
    public void updateView(TaskList taskList){
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter=new HomeFeedAdapter(taskList,getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        isActivity=true;

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}