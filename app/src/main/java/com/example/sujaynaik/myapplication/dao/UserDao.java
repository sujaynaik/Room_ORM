package com.example.sujaynaik.myapplication.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.WorkerThread;

import com.example.sujaynaik.myapplication.model.User;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by sujaynaik on 6/28/17.
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    Flowable<List<User>> getAllUsers();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    Flowable<List<User>> getAllUsersByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :firstName AND last_name LIKE :lastName LIMIT 1")
    Flowable<User> findByName(String firstName, String lastName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
