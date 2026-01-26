package com.hongoquocdat.manage_revenue.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hongoquocdat.manage_revenue.data.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users WHERE name = :userName AND pass = :password")
    User getUser(String userName, String password);

    @Query("SELECT * FROM users ORDER BY id DESC")
    LiveData<List<User>> getAllUsers();

}
