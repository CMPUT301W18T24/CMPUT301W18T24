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

public class RequestedFrag extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private FireBaseManager fmanager;

    private TaskList listUserTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.request_frag,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UserSingleton singleton=UserSingleton.getInstance();
        fmanager = new FireBaseManager(singleton.getmAuth(), getActivity());
        listener(singleton.getUserId());

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("Assigned frag item: ", "clicked " + position);
                UserTask chosenTask= listUserTask.getTask(position);
                Intent myIntent = new Intent(getActivity(), ViewTaskActivity.class);
                myIntent.putExtra("UserTask",chosenTask);
                startActivity(myIntent);
            }
        }));

        return view;

    }
    private void listener(final String requestor) {
        fmanager.getMyTaskData(requestor, new OnGetMyTaskListener() {
            @Override
            public void onSuccess(TaskList taskList) {
                Log.d("Success", "onSuccess: "+taskList.getCount());
                listUserTask = taskList;
                updateView(taskList);
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