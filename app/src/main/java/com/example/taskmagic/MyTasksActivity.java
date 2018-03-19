package com.example.taskmagic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MyTasksActivity extends AppCompatActivity {

    private FireBaseManager fmanager;
    private DatabaseReference db;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FirebaseAuth auth;
    private UserSingleton singleton = UserSingleton.getInstance();
    private String taskTag = "task";

    private String userId;
    private TaskList myTasks;
    private MyTasksListAdapter adapter;
    private ListView tasksListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);

        db = FirebaseDatabase.getInstance().getReference();
        fmanager = new FireBaseManager(singleton.getmAuth(), getApplicationContext());

        listener(singleton.getUserId());

        tasksListView = findViewById(R.id.myTasksList);

        /**
         * This allows user to click on individual item and see the corresponding task details view
         */
        tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
/*
                Intent taskDetailIntent = new Intent(MyTasksActivity.this, ViewTaskActivity.class);
                startActivity(taskDetailIntent);
*/
            }
        });
    }

    private void listener(final String requester) {
        fmanager.getMyTaskData(requester, new onGetMyTaskListener() {
            @Override
            public void onSuccess(TaskList taskList) {
                myTasks = taskList;
                adapter = new MyTasksListAdapter(tasksListView.getContext(), myTasks);
                tasksListView.setAdapter(adapter);

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
