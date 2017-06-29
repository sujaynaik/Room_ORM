package com.example.sujaynaik.myapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.sujaynaik.myapplication.dao.UserDao;
import com.example.sujaynaik.myapplication.model.User;
import com.example.sujaynaik.myapplication.util.Constant;

/**
 * Created by sujaynaik on 6/28/17.
 */

@Database(entities = {User.class}, version = Constant.DB_VERSION)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}
