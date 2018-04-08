package com.example.taskmagic;

/**
 * Created by steve on 2018-03-18.
 */

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
        fmanager.getBidsList(requestor, new OnGetBidsList() {
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

    private void getTasks(BidList bidList) {
        for (int i = 0 ; i < bidList.getCount() ; i++) {
            slowBidlist.add(bidList.getBid(i));
            getTaskListener(bidList.getBid(i).getTaskID());
        }
    }

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