/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * This class is used by ViewTaskActivity to show the task's bids in a recycler view
 * Created by harrold on 4/7/2018.
 */

public class BidsViewAdapter extends RecyclerView.Adapter<BidsViewAdapter.ViewHolder> {
    private BidList bidList;
    private Context context;

    /**
     * Gets an item's position id
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Gets the item type at position
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * Constructor for this class.
     * @param bidList
     * @param context
     */
    public BidsViewAdapter(BidList bidList, Context context) {
        this.bidList = bidList;
        this.context = context;
    }

    /**
     * Initializes the view
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BidsViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_bids_view,parent,false);
        return new BidsViewAdapter.ViewHolder(view);
    }

    /**
     * Binds the data to the view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Bid bid = bidList.getBid(position);

        holder.textTitle.setText(bid.getTaskTitle());
        holder.textProvider.setText(bid.getProviderName());
        holder.textBidValue.setText(String.format("%.2f", bid.getAmount()));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bidDetailsIntent = new Intent(context, BidDetailsActivity.class);
                bidDetailsIntent.putExtra("Bid", bid);
                context.startActivity(bidDetailsIntent);
            }
        });
    }

    /**
     * Returns the number of items in the given list.
     * @return
     */
    @Override
    public int getItemCount() {
        return bidList.getCount();
    }

    /**
     * This class holds the fields from the set view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textProvider;
        TextView textBidValue;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.bid_view_task_title);
            textProvider = itemView.findViewById(R.id.bid_view_provider);
            textBidValue = itemView.findViewById(R.id.bid_view_value);
            relativeLayout = itemView.findViewById(R.id.bid_view_relative_layout);
        }
    }
}
