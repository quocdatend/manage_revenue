package com.hongoquocdat.manage_revenue.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.hongoquocdat.manage_revenue.data.dao.UserDao;
import com.hongoquocdat.manage_revenue.data.database.AppDatabase;
import com.hongoquocdat.manage_revenue.data.entity.User;

import java.util.List;

public class UserRepository {

    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao();
        allUsers = userDao.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insert(User user) {
        AppDatabase.getDatabaseWriteExecutor().execute(() -> {
            userDao.insert(user);
        });
    }

    public void update(User user) {
        AppDatabase.getDatabaseWriteExecutor().execute(() -> {
            userDao.update(user);
        });
    }

    public void delete(User user) {
        AppDatabase.getDatabaseWriteExecutor().execute(() -> {
            userDao.delete(user);
        });
    }
}
