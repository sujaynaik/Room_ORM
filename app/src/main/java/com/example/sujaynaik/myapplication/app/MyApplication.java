package com.example.sujaynaik.myapplication.app;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.example.sujaynaik.myapplication.database.AppDatabase;
import com.example.sujaynaik.myapplication.util.Constant;

/**
 * Created by sujaynaik on 6/28/17.
 */

public class MyApplication extends Application {

    private String TAG = MyApplication.class.getSimpleName();
    public AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        setDb();
    }

    private void setDb() {
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Constant.DB_NAME).build();
    }

    @NonNull
    public AppDatabase getDb() {
        if (!appDatabase.isOpen()) {
            setDb();
        }
        return appDatabase;
    }
}
