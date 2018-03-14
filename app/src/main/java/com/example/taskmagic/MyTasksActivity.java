package com.example.taskmagic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyTasksActivity extends AppCompatActivity {

    private TaskList myTasks;
    private MyTasksListAdapter adapter;
    private ListView tasksListView;

    private MyTasksActivity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);

        //myTasks =             //set my tasks here: not sure how to access user

        tasksListView = findViewById(R.id.myTasksList);
        adapter = new MyTasksListAdapter(tasksListView.getContext(), myTasks.getTaskList()); //get list
        tasksListView.setAdapter(adapter);

        tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Intent taskDetailIntent = new Intent(thisActivity, TaskDetailsActivity);
                //startActivity(taskDetailIntent);

            }
        });
    }
}
