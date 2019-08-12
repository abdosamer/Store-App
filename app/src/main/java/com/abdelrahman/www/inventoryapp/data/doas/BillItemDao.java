package com.abdelrahman.www.inventoryapp.data.doas;




import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.abdelrahman.www.inventoryapp.data.InventoryContract;
import com.abdelrahman.www.inventoryapp.data.InventoryContract.BillsItemEntry;

import java.util.List;


@Dao
public interface BillItemDao {

    @Insert
    void insert(BillsItemEntry billItem);

    @Update
    void update(BillsItemEntry billItem);

    @Delete
    void delet(BillsItemEntry billItem);

    @Query("DELETE  FROM bills_items_table")
    void deletAllBills();

    @Query("SELECT * FROM bills_items_table WHERE _BID = :id")
    LiveData<List<BillsItemEntry>> getbillItemsAt(int id);

    @Query("SELECT * FROM bills_items_table")
    LiveData<List<BillsItemEntry>> getAllBills();

}
