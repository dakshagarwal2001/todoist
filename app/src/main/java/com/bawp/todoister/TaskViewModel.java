package com.bawp.todoister;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    public static TaskRepository repository;
    public final LiveData<List<Task>> allTasks;
    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAllTasks();
    }

    public static void insert(Task task)
    {
        repository.insert(task);
    }
    public LiveData<List<Task>> getAllTasks()
    {
        return allTasks;
    }
    public static void delete(Task task)
    {
        repository.delete(task);
    }
    public static void update(Task task)
    {
        repository.update(task);
    }

    public LiveData<Task> getTask(long id)
    {
        return repository.getTask(id);
    }


}
