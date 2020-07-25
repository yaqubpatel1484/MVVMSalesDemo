package com.myproject.salesdemomvvm.room;

import com.myproject.salesdemomvvm.allclients.AllClientsModel;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {AllClientsModel.class},version = 1)
abstract public class AppDatabase extends RoomDatabase {

    public abstract AppDao appDao();

}
