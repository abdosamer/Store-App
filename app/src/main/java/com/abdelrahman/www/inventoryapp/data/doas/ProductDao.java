package com.abdelrahman.www.inventoryapp.data.doas;




import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.abdelrahman.www.inventoryapp.data.InventoryContract.ProductsEntry;

import java.util.List;



@Dao
public interface ProductDao {

    @Insert
    void insert(ProductsEntry product);

    @Update
    void update(ProductsEntry product);

    @Delete
    void delet(ProductsEntry product);

    @Query("DELETE  FROM products_table")
    void deletAllProducts();


    @Query("SELECT * FROM products_table")
    LiveData<List<ProductsEntry>> getAllProducts();

}
