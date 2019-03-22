package com.example.brunogerassicasamassa.testroompersistence.model;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDAO {


    @Query("SELECT * FROM User")
    public List<User> getAllUsers();

    @Query("SELECT * FROM User WHERE id = :id")
    public User getUserByID(int id);

    @Insert(onConflict = REPLACE)
    public void insert(User user);

    @Update
    public void update(User user);

    @Delete
    public void delete(User user);

    @Query("DELETE FROM User ")
    public void deleteAll();

}
