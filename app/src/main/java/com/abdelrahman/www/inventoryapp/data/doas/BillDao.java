package com.abdelrahman.www.inventoryapp.data.doas;




import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.abdelrahman.www.inventoryapp.data.InventoryContract;
import com.abdelrahman.www.inventoryapp.data.InventoryContract.BillsEntry;

import java.util.List;


@Dao
public interface BillDao {

    @Insert
    void insert(BillsEntry bill);

    @Update
    void update(BillsEntry bill);

    @Delete
    void delet(BillsEntry bill);

    @Query("DELETE  FROM bills_table")
    void deletAllBills();


    @Query("SELECT * FROM bills_table WHERE _BID = :id")
    LiveData<List<BillsEntry>> getbillAt(int id);


    @Query("SELECT * FROM bills_table")
    LiveData<List<BillsEntry>> getAllBills();

}
