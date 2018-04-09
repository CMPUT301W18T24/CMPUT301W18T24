package com.example.taskmagic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by hyusuf on 2018-03-11.
 */

public class SearchActivity extends AppCompatActivity {
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
    private TaskList taskList;
    private TaskList taskList1= new TaskList();
    private EditText search;

    /**
     * this function sets up the home feed
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final UserSingleton singleton = UserSingleton.getInstance();
        auth = singleton.getmAuth();
        search = (EditText) findViewById(R.id.search_menu);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        fmanager = new FireBaseManager(singleton.getmAuth(), getApplicationContext());
        listener(singleton.getUserId());


        //https://medium.com/@harivigneshjayapalan/android-recyclerview-implementing-single-item-click-and-long-press-part-ii-b43ef8cb6ad8
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("click: ", "clicked" + position);
                UserTask chosenTask= taskList1.getTask(position);
                Intent myIntent = new Intent(SearchActivity.this, ViewTaskActivity.class);
                myIntent.putExtra("UserTask",chosenTask);
                startActivity(myIntent);
            }
        }));
        fmanager.getRequestedTasks(singleton.getUserId(), new OnGetAllTaskReqListener() {
            @Override
            public void onSuccess(TaskList taskList) {
                Log.d("Succes", "onSuccess: "+taskList.getCount());
                TaskList list=new TaskList();
                list=taskList;
                listUserTask = taskList;
            }

            @Override
            public void onFailure(String message) {

            }
        });
        // set the filter edit text listner
        search.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // before the text change, do nothing

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

                //once user type some letter in the edit text, it will be return to this function as CharSequence s
                taskList1 = new TaskList();
                for (int i = 0; i < listUserTask.getCount(); i++){ // check every task in tasklist
                    if (listUserTask.getTask(i).getStatus().contains("Bidded") || listUserTask.getTask(i).getStatus().contains("Requested")) {
                        if (listUserTask.getTask(i).getTitle().contains(s)) {// for every task, if the name of the task contains the Char, then add it
                            taskList1.add(listUserTask.getTask(i));
                        } else if (listUserTask.getTask(i).getDescription().contains(s)) {
                            taskList1.add(listUserTask.getTask(i));
                        }
                    }
                }



                adapter.notifyDataSetChanged();
                adapter = new TaskRecyclerAdapter(taskList1,getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){ // if nothing entered into the edit text, then display the orginal
                    taskList1 = listUserTask;



                    //set up the adapter
                    adapter = new TaskRecyclerAdapter(taskList1,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            }
        });


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
                taskList1 = taskList;
                updateView(taskList1);
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
        adapter=new TaskRecyclerAdapter(taskList,getApplicationContext());
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

