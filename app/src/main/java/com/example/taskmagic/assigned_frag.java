package com.example.taskmagic;

/**
 * Created by steve on 2018-03-18.
 */


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class assigned_frag extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private FireBaseManager fmanager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.assigned_frag,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerAssigned);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UserSingleton singleton=UserSingleton.getInstance();
        fmanager = new FireBaseManager(singleton.getmAuth(), getActivity());
        listener(singleton.getUserId());
        return view;
    }
    private void listener(final String requestor) {
        fmanager.getAssignedTasks(requestor, new OnGetAssignedTaskListener() {
            @Override
            public void onSuccess(TaskList taskList) {
                Log.d("Succes", "onSuccess: "+taskList.getCount());
                updateView(taskList);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    public void updateView(TaskList taskList){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new AssignedTaskAdapter(taskList,getActivity());
        recyclerView.setAdapter(adapter);
    }
}