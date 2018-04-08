package com.example.taskmagic;

/**
 * Created by steve on 2018-03-18.
 */

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class RequestedFrag extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private FireBaseManager fmanager;
    private CheckBox assignedCheckBox;
    private CheckBox biddedCheckBox;
    private CheckBox requestedCheckBox;
    private TaskList listToShow;
    private TaskList listUserTask;
    private TaskList biddedList;
    private TaskList requestedList;
    private TaskList assignedList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.request_frag,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        assignedCheckBox = (CheckBox) view.findViewById(R.id.checkbox_assigned);
        biddedCheckBox = (CheckBox) view.findViewById(R.id.checkbox_bidded);
        requestedCheckBox = (CheckBox) view.findViewById(R.id.checkbox_requested);
        UserSingleton singleton=UserSingleton.getInstance();
        fmanager = new FireBaseManager(singleton.getmAuth(), getActivity());
        listener(singleton.getUserId());

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("Assigned frag item: ", "clicked " + position);
                UserTask chosenTask = listToShow.getTask(position);
                Intent myIntent = new Intent(getActivity(), ViewTaskActivity.class);
                myIntent.putExtra("UserTask",chosenTask);
                startActivity(myIntent);
                updateView(listToShow);
            }
        }));

        assignedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (assignedCheckBox.isChecked()) {
                    biddedCheckBox.setChecked(false);
                    requestedCheckBox.setChecked(false);
                    listToShow = assignedList;
                    updateView(assignedList);
                }
            }
        });

        requestedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (requestedCheckBox.isChecked()) {
                    biddedCheckBox.setChecked(false);
                    assignedCheckBox.setChecked(false);
                    listToShow = requestedList;
                    updateView(requestedList);
                };
            }
        });

        biddedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (biddedCheckBox.isChecked()) {
                    requestedCheckBox.setChecked(false);
                    assignedCheckBox.setChecked(false);
                    listToShow = biddedList;
                    updateView(biddedList);
                }
            }
        });

        return view;

    }
    private void listener(final String requestor) {
        fmanager.getMyTaskData(requestor, new OnGetMyTaskListener() {
            @Override
            public void onSuccess(TaskList taskList) {
                Log.d("Success", "onSuccess: "+taskList.getCount());
                listUserTask = taskList;
                biddedList = new TaskList();
                requestedList = new TaskList();
                assignedList = new TaskList();
                for (int i = 0; i < taskList.getCount(); i++) {
                    UserTask task = taskList.getTask(i);
                    if (task.getStatus().equals("Requested")) {
                        requestedList.add(task);
                    } else if (task.getStatus().equals("Bidded")) {
                        biddedList.add(task);
                    } else if (task.getStatus().equals("Assigned")) {
                        assignedList.add(task);
                    }
                }
                biddedCheckBox.setChecked(false);
                requestedCheckBox.setChecked(false);
                assignedCheckBox.setChecked(false);
                listToShow = listUserTask;
                updateView(listUserTask);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    public void updateView(TaskList taskList){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new RequestedTaskAdapter(taskList,getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}