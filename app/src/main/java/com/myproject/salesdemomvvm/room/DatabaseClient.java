package com.myproject.salesdemomvvm.room;

import com.myproject.salesdemomvvm.application.MyApplication;

import androidx.room.Room;

public class DatabaseClient {

    private static DatabaseClient mInstance;

    private AppDatabase appDatabase;

    private DatabaseClient(){
        appDatabase = Room.databaseBuilder(MyApplication.getInstance(),
                AppDatabase.class,"client_db").build();
    }

    public static synchronized DatabaseClient getInstance(){
        if(mInstance == null){
            mInstance =  new DatabaseClient();
        }

        return mInstance;
    }

    public AppDatabase getAppDatabase(){
        return appDatabase;
    }

}
