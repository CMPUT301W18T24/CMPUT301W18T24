package com.example.taskmagic;

/**
 * Created by hyusuf on 2018-03-13.
 */


public interface OnGetAllTaskReqListener {
        public void onSuccess(TaskList taskList);
        public void onFailure(String message);
}


