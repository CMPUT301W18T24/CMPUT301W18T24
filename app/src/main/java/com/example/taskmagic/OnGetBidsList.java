package com.example.taskmagic;

/**
 * Created by hyusuf on 2018-03-14.
 */

public interface OnGetBidsList {
    public void onSuccess(BidList Bids);
    public void onFailure(String message);
}
