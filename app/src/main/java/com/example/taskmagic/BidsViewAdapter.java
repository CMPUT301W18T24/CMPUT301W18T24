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
 * Created by harrold on 4/7/2018.
 */

public class BidsViewAdapter extends RecyclerView.Adapter<BidsViewAdapter.ViewHolder> {
    private BidList bidList;
    private Context context;

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public BidsViewAdapter(BidList bidList, Context context) {
        this.bidList = bidList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_bids_view,parent,false);
        return new BidsViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Bid bid = bidList.getBid(position);

        holder.textTitle.setText(bid.getTaskTitle());
        holder.textProvider.setText(bid.getProvider());
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

    @Override
    public int getItemCount() {
        return bidList.getCount();
    }

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
