package com.example.brunogerassicasamassa.testroompersistence.utils;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.brunogerassicasamassa.testroompersistence.model.User;
import com.example.brunogerassicasamassa.testroompersistence.model.UserDAO;

@android.arch.persistence.room.Database(entities = {User.class}, version = 1)
public abstract class Database extends RoomDatabase {

     SharedPreferences pref;

     SharedPreferences.Editor editor;

     Context _context;

    private String cpf;

    private static final String PREF_NAME = "backup";


    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //database.execSQL("ALTER TABLE User ADD COLUMN hiredDay INTEGER");
        }
    };

    private static Database database;


    public abstract UserDAO createUserDAO();


    public static Database getInstance(Context context) {

        if (database == null) {

            database = Room.databaseBuilder(context.getApplicationContext(), Database.class, "user_database")
                    .fallbackToDestructiveMigration()  //ignora lista antiga
                    //.addMigrations(MIGRATION_1_2)        //update na lista caso model seja alterado
                    .allowMainThreadQueries()
                    .build();
        }

        return database;
    }



    public Float getCoodinate(String key) {
        return pref.getFloat(key,  0);
    }


    public void putCoordinate(String key, Double coordinate) {
        editor.putFloat(key, coordinate.floatValue());

        editor.commit();
    }


}
