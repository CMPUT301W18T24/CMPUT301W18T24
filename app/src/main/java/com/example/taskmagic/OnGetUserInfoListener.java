package com.example.taskmagic;

/**
 * Created by hyusuf on 2018-03-11.
 */

public interface OnGetUserInfoListener {
    public void onSuccess(User user);
    public void onFailure(String message);
}
