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
 * Created by hyusuf on 2018-03-19.
 */

/**
 * THis class is used to display The users bids for the view
 */
public class BiddedAdapter extends RecyclerView.Adapter<BiddedAdapter.ViewHolder> {
    private BidList bidList;
    private TaskList tasks;
    private Context context;

    /**
     * used to get an items position id
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * used to get type of item in the position
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * the bidAdapter constructor
     * @param bidsList
     * @param context
     */
    public BiddedAdapter(BidList bidsList, TaskList tasks, Context context) {
        this.bidList = bidsList;
        this.tasks = tasks;
        this.context = context;
    }



    @Override
    public BiddedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_bidded,parent,false);
        return  new BiddedAdapter.ViewHolder(view);
    }

    /**
     * binds the data to the view
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final BiddedAdapter.ViewHolder holder, final int position) {
        final Bid bid = bidList.getBid(position);
        final UserTask task = tasks.getTask(position);

        holder.textTitle.setText(bid.getTaskTitle());
        holder.textRequester.setText(bid.getRequestorName());
        holder.textStatus.setText(task.getStatus());

        holder.textOwnBid.setText(String.format("%.2f", bid.getAmount()));
        holder.textLowestBid.setText(String.format("%.2f", task.getLowestBid()));

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
     *  This function returns the number of items in the Bids list
     * @return int
     */
    @Override
    public int getItemCount() {
        return bidList.getCount();
    }

    /**
     * This class is responsible for setting item view and data on its place in the recyclerView
     *
     */

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textRequester;
        TextView textStatus;
        TextView textOwnBid;
        TextView textLowestBid;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.bidded_task_title);
            textRequester = itemView.findViewById(R.id.bidded_task_requester);
            textStatus = itemView.findViewById(R.id.bidded_task_status);
            textOwnBid = itemView.findViewById(R.id.bidded_current_own);
            textLowestBid = itemView.findViewById(R.id.bidded_current_lowest);
            relativeLayout = itemView.findViewById(R.id.bid_relative_layout);
        }
    }

}
