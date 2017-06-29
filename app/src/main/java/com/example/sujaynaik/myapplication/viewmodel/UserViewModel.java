package com.example.sujaynaik.myapplication.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.example.sujaynaik.myapplication.dao.UserDao;
import com.example.sujaynaik.myapplication.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.completable.CompletableFromAction;

/**
 * Created by sujaynaik on 6/28/17.
 */

public class UserViewModel extends ViewModel {

    private UserDao userDao;

    public UserViewModel(UserDao userDao) {
        this.userDao = userDao;
    }

    public Flowable<List<User>> getAllUsers() {
        return userDao.getAllUsers().map(new Function<List<User>, List<User>>() {
                    @Override
                    public List<User> apply(@NonNull List<User> userList) throws Exception {
                        return userList;
                    }
                });
    }

    public Completable insertUsers(final User... users){
        return new CompletableFromAction(new Action() {
            @Override
            public void run() throws Exception {
                userDao.insertAll(users);
            }
        });
    }

    public Completable deleteUser(final User user){
        return new CompletableFromAction(new Action() {
            @Override
            public void run() throws Exception {
                userDao.delete(user);
            }
        });
    }
}
