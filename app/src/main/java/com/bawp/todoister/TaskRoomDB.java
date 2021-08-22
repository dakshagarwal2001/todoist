package com.bawp.todoister;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class},version = 1,exportSchema = false)
@TypeConverters({Converter.class})
public abstract class TaskRoomDB extends RoomDatabase {
    public abstract TaskDao taskDao();
    public static final int NO_OF_THREADS = 4;
    public static final String DB_NAME = "todoister_db";
    public static volatile TaskRoomDB INSTANCE;
    public static final ExecutorService databaseWriterExecutor =
            Executors.newFixedThreadPool(NO_OF_THREADS);
    public static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback()
            {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriterExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            TaskDao taskDao = INSTANCE.taskDao();
                            taskDao.deleteAll();
                        }
                    });
                }
            };


    public static TaskRoomDB getDatabase(final Context context)
    {
        if(INSTANCE==null)
        {
            synchronized (TaskRoomDB.class)
            {
                if(INSTANCE==null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),TaskRoomDB.class,DB_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
