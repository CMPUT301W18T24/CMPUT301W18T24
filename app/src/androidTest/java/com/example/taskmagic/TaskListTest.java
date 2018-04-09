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
        UserTask taskA = new UserTask("TaskA", "Creating a task.", "123ab", "John Doe", "3nc0d3dbyt35", "2018/04/26");
        UserTask taskB = new UserTask("TaskB", "Creating a task.", "123bc", "Jane Doe", "3nc0d3dbyt35", "2018/04/26");
        UserTask taskC = new UserTask("TaskC", "Creating a task.", "123cd", "Jesse Doe", "3nc0d3dbyt35", "2018/04/26");

        TaskList taskList = new TaskList();
        taskList.add(taskA);
        assertEquals(taskList.getCount(), 1);
        taskList.add(taskB);
        assertEquals(taskList.getCount(), 2);
        taskList.add(taskC);
        assertEquals(taskList.getCount(), 3);

    }

    public void testAddTask() {
        UserTask task = new UserTask("TaskA", "Creating a task.", "123ab", "John Doe", "3nc0d3dbyt35", "2018/04/26");
        TaskList taskList = new TaskList();

        taskList.add(task);
        assertTrue(taskList.hasTask(task));

    }

    public void testRemoveTask() {
        UserTask task = new UserTask("TaskA", "Creating a task.", "123ab", "John Doe", "3nc0d3dbyt35", "2018/04/26");
        TaskList taskList = new TaskList();

        taskList.add(task);
        assertTrue(taskList.hasTask(task));

        taskList.remove(task);
        assertFalse(taskList.hasTask(task));

    }

    public void testGetTask() {
        UserTask task = new UserTask("TaskA", "Creating a task.", "123ab", "John Doe", "3nc0d3dbyt35", "2018/04/26");
        task.setId("1a2b3c");
        TaskList taskList = new TaskList();

        taskList.add(task);
        assertEquals(taskList.getTask(0).getId(), task.getId());
    }

    public void testGetTaskList() {
        ArrayList<UserTask> tasks = new ArrayList<UserTask>();
        UserTask taskA = new UserTask("TaskA", "Creating a task.", "123ab", "John Doe", "3nc0d3dbyt35", "2018/04/26");
        UserTask taskB = new UserTask("TaskB", "Creating a task.", "123bc", "Jane Doe", "3nc0d3dbyt35", "2018/04/26");
        UserTask taskC = new UserTask("TaskC", "Creating a task.", "123cd", "Jesse Doe", "3nc0d3dbyt35", "2018/04/26");
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
        UserTask task = new UserTask("TaskA", "Creating a task.", "123ab", "John Doe", "3nc0d3dbyt35", "2018/04/26");
        TaskList taskList = new TaskList();

        assertFalse(taskList.hasTask(task));
        taskList.add(task);
        assertTrue(taskList.hasTask(task));
    }

}
