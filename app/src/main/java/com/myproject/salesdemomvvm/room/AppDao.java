package com.myproject.salesdemomvvm.room;

import com.myproject.salesdemomvvm.allclients.AllClientsModel;

import java.util.ArrayList;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AppDao {

    @Insert
    void insertClient(AllClientsModel model);

    @Query("Select * FROM AllClientsModel")
    ArrayList<AllClientsModel> getClients(String username);

}
