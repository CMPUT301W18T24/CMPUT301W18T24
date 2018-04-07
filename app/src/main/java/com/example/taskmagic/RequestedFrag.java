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
    private CheckBox allCheckBox;
    private CheckBox biddedCheckBox;
    private CheckBox requestedCheckBox;
    private TaskList listToShow;
    private TaskList listUserTask;
    private TaskList biddedList;
    private TaskList requestedList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.request_frag,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        allCheckBox = (CheckBox) view.findViewById(R.id.checkbox_all);
        biddedCheckBox = (CheckBox) view.findViewById(R.id.checkbox_bidded);
        requestedCheckBox = (CheckBox) view.findViewById(R.id.checkbox_requested);
        UserSingleton singleton=UserSingleton.getInstance();
        fmanager = new FireBaseManager(singleton.getmAuth(), getActivity());
        listener(singleton.getUserId());

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("Assigned frag item: ", "clicked " + position);
                UserTask chosenTask= listToShow.getTask(position);
                Intent myIntent = new Intent(getActivity(), ViewTaskActivity.class);
                myIntent.putExtra("UserTask",chosenTask);
                startActivity(myIntent);
                updateView();
            }
        }));

        allCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (allCheckBox.isChecked()) {
                    biddedCheckBox.setChecked(true);
                    requestedCheckBox.setChecked(true);
                }
                updateView();
            }
        });

        requestedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!requestedCheckBox.isChecked()) {
                    if (biddedCheckBox.isChecked()) {
                        allCheckBox.setChecked(false);
                    } else if (biddedCheckBox.isChecked()) {
                        allCheckBox.setChecked(true);
                    }
                }
                updateView();
            }
        });

        biddedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!biddedCheckBox.isChecked()) {
                    if (requestedCheckBox.isChecked()) {
                        allCheckBox.setChecked(false);
                    } else if (requestedCheckBox.isChecked()) {
                        allCheckBox.setChecked(true);
                    }
                }
                updateView();
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
                for (int i = 0; i < taskList.getCount(); i++) {
                    if (taskList.getTask(i).getStatus().equals("Requested")) {
                        requestedList.add(taskList.getTask(i));
                    } else if (taskList.getTask(i).getStatus().equals("bidded")) {
                        biddedList.add(taskList.getTask(i));
                    }
                }
                updateView();
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    public void updateView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (biddedCheckBox.isChecked() && requestedCheckBox.isChecked()) {
            listToShow = listUserTask;
        } else if (biddedCheckBox.isChecked()) {
            listToShow = biddedList;
        } else if (requestedCheckBox.isChecked()) {
            listToShow = requestedList;
        } else {
            listToShow = new TaskList();

        }
        adapter=new RequestedTaskAdapter(listToShow,getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}