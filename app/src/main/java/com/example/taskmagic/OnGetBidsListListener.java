package com.example.taskmagic;

/**
 * Created by hyusuf on 2018-03-14.
 */

public interface OnGetBidsListListener {
    public void onSuccess(BidList Bids);
    public void onFailure(String message);
}
