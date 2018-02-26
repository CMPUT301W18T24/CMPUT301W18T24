package com.example.taskmagic;

import java.util.ArrayList;

/**
 * Created by Yipu on 22/02/2018.
 */

class TaskList {
    private ArrayList<Task> tasks = new ArrayList<Task>();

    public void add(Task task) { tasks.add(task); }

    public void remove(Task task) {
        tasks.remove(task);
    }

    public Boolean hasTask(Task task) {
        return tasks.contains(task);
    }

    public Task getTask(int index) {
        if (index < tasks.size()) {
            return tasks.get(index);
        }
        else {
            return null;
        }
    }

    public ArrayList<Task> getTaskList() {
        return tasks;
    }

    public int getCount() {
        return tasks.size();
    }
}
