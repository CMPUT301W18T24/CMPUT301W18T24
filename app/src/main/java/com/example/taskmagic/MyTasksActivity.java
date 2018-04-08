package com.example.taskmagic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * This activity is used to display the current user's requested tasks.
 */
public class MyTasksActivity extends AppCompatActivity {
    private FireBaseManager fmanager;
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

    /**
     * This method listens for changes in database results; this is used to filter user's own tasks
     * @param requester -> the unique ID of the current user
     */
    private void listener(final String requester) {
        fmanager.getMyTaskData(requester, new OnGetMyTaskListener() {
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
