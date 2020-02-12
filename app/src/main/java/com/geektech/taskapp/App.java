package com.geektech.taskapp;

import android.app.Application;

import androidx.room.Room;

import com.geektech.taskapp.room.AppDataBase;

public class App extends Application {
    public static volatile App instance;
    private static AppDataBase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database= Room.databaseBuilder(this,AppDataBase.class,"database").allowMainThreadQueries().build();
    }

    public static App getInstance() {
        return instance;
    }

    public static AppDataBase getDatabase() {
        return database;
    }
}
