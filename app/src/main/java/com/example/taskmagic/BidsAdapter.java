package com.example.taskmagic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

/**
 * Created by hyusuf on 2018-03-19.
 */

/**
 * THis class is used to display The users bids for the view
 */
public class BidsAdapter extends RecyclerView.Adapter<BidsAdapter.ViewHolder> {
    private BidList bidList;
    private Context context;
    private Bid chosenBid;

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
    public BidsAdapter(BidList bidsList, Context context) {
        this.bidList =bidsList;
        this.context = context;
    }



    @Override
    public BidsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item,parent,false);
        return  new BidsAdapter.ViewHolder(view);
    }

    /**
     * binds the data to the view
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final BidsAdapter.ViewHolder holder, final int position) {
        final Bid bid = bidList.getBid(position);

        holder.textTitle.setText(bid.getTaskTitle());
        holder.textAmount.setText("Bid Amount: "+bid.getAmount());

        holder.textOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.textOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.viewbid_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.mnu_item_viewbid:
                                Intent myIntent = new Intent(context, BidDetailsActivity.class);
                                myIntent.putExtra("Bid",bid);
                                context.startActivity(myIntent);
                                break;

                            case R.id.mnu_item_accept:
                                break;

                            case R.id.mnu_item_contact:
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

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
        public TextView textTitle;
        public TextView textAmount;
        public TextView textOption;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle=(TextView)itemView.findViewById(R.id.taskTitle);
            textAmount=(TextView)itemView.findViewById(R.id.status);
            textOption=(TextView)itemView.findViewById(R.id.textOption);
        }
    }

}
