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

public class Bid_Frag extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private FireBaseManager fmanager;

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
        adapter=new BidsAdapter(bidList,getActivity());
        recyclerView.setAdapter(adapter);
    }
}