package com.abdelrahman.www.inventoryapp.data.doas;




import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.abdelrahman.www.inventoryapp.data.InventoryContract.LogEntry;

import java.util.List;


@Dao
public interface LogDao {

    @Insert
    void insert(LogEntry log);

    @Update
    void update(LogEntry log);

    @Delete
    void delet(LogEntry log);

    @Query("DELETE  FROM log_table")
    void deletAllLogs();


    @Query("SELECT * FROM log_table")
    LiveData<List<LogEntry>> getAllLogs();

}
