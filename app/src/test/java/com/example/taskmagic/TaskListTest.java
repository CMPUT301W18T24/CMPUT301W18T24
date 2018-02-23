package com.example.taskmagic;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Yipu on 22/02/2018.
 */

public class TaskListTest extends ActivityInstrumentationTestCase2 {
    public TaskListTest() {
        super(MainActivity.class);
    }

    public void testGetCount() {
        Task taskA = new Task(1,"TaskA", "Creating a task.", "Requested");
        Task taskB = new Task(2,"TaskB", "Creating a task.", "Requested");
        Task taskC = new Task(2,"TaskC", "Creating a task.", "Requested");

        TaskList taskList = new TaskList();
        taskList.add(taskA);
        assertEquals(taskList.getCount(), 1);
        taskList.add(taskB);
        assertEquals(taskList.getCount(), 2);
        taskList.add(taskC);
        assertEquals(taskList.getCount(), 3);

    }

    public void testAdd() {
        Task task = new Task(1,"TaskA", "Creating a task.", "Requested");
        TaskList taskList = new TaskList();

        taskList.add(task);
        assertTrue(taskList.hasTask(task));

    }

    public void testRemoveTask() {
        Task task = new Task(1,"TaskA", "Creating a task.", "Requested");
        TaskList taskList = new TaskList();

        taskList.add(task);
        assertTrue(taskList.hasTask(task));

        taskList.remove(task);
        assertFalse(taskList.hasTask(task));

    }

}
