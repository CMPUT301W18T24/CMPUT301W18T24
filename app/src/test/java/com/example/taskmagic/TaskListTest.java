package com.example.taskmagic;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

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

    public void testAddTask() {
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

    public void testGetTask() {
        Task task = new Task(1,"TaskA", "Creating a task.", "Requested");
        TaskList taskList = new TaskList();

        assertEquals(taskList.getTask(0).getID(), task.getID());
    }

    public void testGetTaskList() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        Task taskA = new Task(1,"TaskA", "Creating a task.", "Requested");
        Task taskB = new Task(2,"TaskB", "Creating a task.", "Requested");
        Task taskC = new Task(3,"TaskC", "Creating a task.", "Requested");
        TaskList taskList = new TaskList();

        taskList.add(taskA);
        tasks.add(taskA);
        taskList.add(taskB);
        tasks.add(taskB);
        taskList.add(taskC);
        tasks.add(taskC);

        assertEquals(taskList.getTaskList(), tasks);
    }

    public void testHasTask() {
        Task task = new Task(1,"TaskA", "Creating a task.", "Requested");
        TaskList taskList = new TaskList();

        assertFalse(taskList.hasTask(task));
        taskList.add(task);
        assertTrue(taskList.hasTask(task));
    }

}
