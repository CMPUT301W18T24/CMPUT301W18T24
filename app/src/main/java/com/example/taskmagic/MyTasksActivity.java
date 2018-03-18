package com.example.taskmagic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private String taskTag = "task";

    private String userId;
    private TaskList myTasks;
    private MyTasksListAdapter adapter;
    private ListView tasksListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);

        UserSingleton singleton = UserSingleton.getInstance();
        userId = singleton.getUserId();

        DatabaseReference ref = db.child(taskTag).getRef();
        Query query = ref.orderByChild("requester").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot uTask : dataSnapshot.getChildren()) {
                        UserTask userTask = uTask.getValue(UserTask.class);
                        myTasks.add(userTask);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tasksListView = findViewById(R.id.myTasksList);
        adapter = new MyTasksListAdapter(tasksListView.getContext(), myTasks.getTaskList());
        tasksListView.setAdapter(adapter);

        tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent taskDetailIntent = new Intent(MyTasksActivity.this, ViewTaskActivity.class);
                startActivity(taskDetailIntent);

            }
        });
    }
}
