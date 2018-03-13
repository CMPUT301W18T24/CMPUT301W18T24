package com.example.taskmagic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyTasksActivity extends AppCompatActivity {

    private TaskList myTasks;
    private ArrayAdapter<UserTask> adapter;
    private ListView tasksListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);

        tasksListView = findViewById(R.id.myTasksList);
        adapter = new ArrayAdapter<UserTask>(tasksListView.getContext(), R.layout.my_task_list_item, myTasks.getTaskList());
        tasksListView.setAdapter(adapter);

        tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //code for moving onto taskDetails activity

            }
        });
    }
}
