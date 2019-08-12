package com.abdelrahman.www.inventoryapp.data;

import android.app.Application;


import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.abdelrahman.www.inventoryapp.data.InventoryContract.*;
import java.util.List;




public class InventoryViewModel extends AndroidViewModel {

    InventoryRepository inventoryRepository;
    LiveData<List<ProductsEntry>> allProducts;

    LiveData<List<BillsEntry>> allBills;

    LiveData<List<BillsItemEntry>> allBillsItem;

    LiveData<List<LogEntry>> allLogs;

    public InventoryViewModel(@NonNull Application application) {
        super(application);
        inventoryRepository = new InventoryRepository(application);
        allProducts = inventoryRepository.getAllProducts();
        allBills = inventoryRepository.getAllBills();
        allBillsItem = inventoryRepository.getAllBillItems();
        allLogs = inventoryRepository.getAllLogsItems();
    }

    /**
     * all the view model product api methods that we will contact it in the activity
     *
     * @param productsEntry
     */
    public void insert(ProductsEntry productsEntry)
    {
        inventoryRepository.insert(productsEntry);
    }

    public void update(ProductsEntry productsEntry)
    {
        inventoryRepository.update(productsEntry);
    }

    public void delet(ProductsEntry productsEntry)
    {
        inventoryRepository.delete(productsEntry);
    }
    public void deletAll()
    {
        inventoryRepository.deleteAllProducts();
    }

    public LiveData<List<ProductsEntry>> getAllProducts()
    {
        return allProducts;
    }


    /**
     * all the view model Bills api methods that we will contact it in the activity
     *
     * @param billsEntry
     */
    public void insert(BillsEntry billsEntry)
    {
        inventoryRepository.insert(billsEntry);
    }

    public void update(BillsEntry billsEntry)
    {
        inventoryRepository.update(billsEntry);
    }

    public void delet(BillsEntry billsEntry)
    {
        inventoryRepository.delete(billsEntry);
    }
    public void deletAllBills()
    {
        inventoryRepository.deleteAllBills();
    }

    public LiveData<List<BillsEntry>> getAllBills()
    {
        return allBills;
    }


    /**
     * all the view model bills items api methods that we will contact it in the activity
     *
     * @param billsItemEntry
     */
    public void insert(BillsItemEntry billsItemEntry)
    {
        inventoryRepository.insert(billsItemEntry);
    }

    public void update(BillsItemEntry billsItemEntry)
    {
        inventoryRepository.update(billsItemEntry);
    }

    public void delet(BillsItemEntry billsItemEntry)
    {
        inventoryRepository.delete(billsItemEntry);
    }
    public void deletAllBillsItems()
    {
        inventoryRepository.deleteAllBillItems();
    }

    public BillsEntry getAllBillsAtId(int _id)
    {
        return inventoryRepository.getbillItemsAt(_id);
    }




    public LiveData<List<BillsItemEntry>> getAllBillsItemAtId(int _id)
    {
       return inventoryRepository.getAllBillsItemAtId(_id);
    }


    public LiveData<List<BillsItemEntry>> getAllBillsItems()
    {
        return allBillsItem;
    }


    /**
     * all the view model product api methods that we will contact it in the activity
     *
     * @param logEntry
     */
    public void insert(LogEntry logEntry)
    {
        inventoryRepository.insert(logEntry);
    }

    public void update(LogEntry logEntry)
    {
        inventoryRepository.update(logEntry);
    }

    public void delet(LogEntry logEntry)
    {
        inventoryRepository.delete(logEntry);
    }
    public void deletAllLogs()
    {
        inventoryRepository.deleteAllLogsItems();
    }

    public LiveData<List<LogEntry>> getAllLogs()
    {
        return allLogs;
    }



}
