/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */
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

public class BidFrag extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private FireBaseManager fmanager;
    private BidList slowBidlist = new BidList();
    private TaskList tasks = new TaskList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bids_frag,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerviewBids);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UserSingleton singleton=UserSingleton.getInstance();
        fmanager = new FireBaseManager(singleton.getmAuth(), getActivity());
        listener(singleton.getUserId());
        return view;
    }

    /**
     * Updates the listView to display the User's BidList
     * @param requestor
     */
    private void listener(final String requestor) {
        fmanager.getBidsList(requestor, new OnGetBidsListListener() {
            @Override
            public void onSuccess(BidList bidList) {
                Log.d("Succes", "onSuccess: "+bidList.getCount());
                updateView(bidList);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    /**
     * Sets the list in the adapter to specified BidList
     * @param bidList
     */
    public void updateView(BidList bidList){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getTasks(bidList);
    }

    // Filtering the retrieved bidlist from firebase

    /**
     *
     * @param bidList
     */
    private void getTasks(BidList bidList) {
        slowBidlist = new BidList();
        for (int i = 0 ; i < bidList.getCount() ; i++) {
            slowBidlist.add(bidList.getBid(i));
            getTaskListener(bidList.getBid(i).getTaskID());
        }
    }

    /**
     * This retrieves the task info from firebase
     * @param taskId
     */
    private void getTaskListener(String taskId) {
        fmanager.getTaskInfo(taskId, new OnGetATaskListener() {
            @Override
            public void onSuccess(UserTask task) {
                tasks.add(task);
                adapter = new BiddedAdapter(slowBidlist, tasks, getActivity());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                Log.d("Fail", String.format("Task was not found: Bidlist[%d].", slowBidlist.getCount()));
            }
        });
    }
}