package com.bawp.todoister;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {

    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    public TaskRepository(Application application) {
        TaskRoomDB database = TaskRoomDB.getDatabase(application);
        this.taskDao = database.taskDao();
        this.allTasks = taskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks()
    {
        return allTasks;
    }
    public void insert(Task task)
    {
        TaskRoomDB.databaseWriterExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.insertTask(task);
            }
        });
    }
    public LiveData<Task> getTask(long id)
    {
        return taskDao.getTask(id);
    }

    public void delete(Task task)
    {
        TaskRoomDB.databaseWriterExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.deleteTask(task);
            }
        });
    }
    public void update(Task task)
    {
        TaskRoomDB.databaseWriterExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.update(task);
            }
        });
    }



}
