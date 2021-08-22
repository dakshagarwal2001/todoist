package com.bawp.todoister;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTask(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Query("SELECT * FROM task_table ORDER BY due_date ASC")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM task_table WHERE task_table.task_id == :taskId")
    LiveData<Task> getTask(long taskId);

    @Update
    void update(Task task);

    @Delete
    void deleteTask(Task task);
}
