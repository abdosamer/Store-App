package com.abdelrahman.www.inventoryapp.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;


import  com.abdelrahman.www.inventoryapp.data.InventoryContract.*;
import com.abdelrahman.www.inventoryapp.data.doas.BillDao;
import com.abdelrahman.www.inventoryapp.data.doas.BillItemDao;
import com.abdelrahman.www.inventoryapp.data.doas.LogDao;
import com.abdelrahman.www.inventoryapp.data.doas.ProductDao;


public class InventoryRepository {

    /** init data access object dao of products to use it in
     *  InventoryRepository class
     * */

    ProductDao productDao;
    LiveData<List<ProductsEntry>> allProducts ;

    BillDao billDao;
    LiveData<List<BillsEntry>> allBills ;

    BillItemDao billItemDao;
    LiveData<List<BillsItemEntry>> allBillsItem ;

    LogDao logDao;
    LiveData<List<LogEntry>> allLogs ;



    public InventoryRepository(Application application) {

        InventoryDataBase dataBase = InventoryDataBase.getInstance(application);

        /**
         *   init Dao "database tables"
         */

        productDao = dataBase.productDao();
        allProducts = productDao.getAllProducts();

        billDao = dataBase.billDao();
        allBills = billDao.getAllBills();

        billItemDao = dataBase.billItemDao();
        allBillsItem = billItemDao.getAllBills();

        logDao = dataBase.logDao();
        allLogs = logDao.getAllLogs();

    }


    /**
     * this methods is for Product functions insert,delete,update,delete all, get all products
     *
     * @param productsEntry
     */
    public void insert(ProductsEntry productsEntry)
    {
        new insertProductAsynctask(productDao).execute(productsEntry);
    }

    public void delete(ProductsEntry productsEntry)
    {
        new deletProductAsynctask(productDao).execute(productsEntry);
    }

    public void update(ProductsEntry productsEntry)
    {
        new updateProductAsynctask(productDao).execute(productsEntry);
    }

    public void deleteAllProducts()
    {
        new deletAllProductAsynctask(productDao).execute();
    }

    public LiveData<List<ProductsEntry>> getAllProducts()
    {
        return allProducts;
    }

    /**
     *  we cant do database operations in the main thread so we do it in the background thread
     *  for product database functions insert,delete,update,delete all, get all products
     */
    public static class insertProductAsynctask extends AsyncTask<ProductsEntry,Void,Void>
    {
        private ProductDao productDao;
        private insertProductAsynctask(ProductDao productDao)
        {
            this.productDao = productDao;
        }
        @Override
        protected Void doInBackground(ProductsEntry... productsEntries) {

            productDao.insert(productsEntries[0]);

            return null;
        }
    }


    public static class updateProductAsynctask extends AsyncTask<ProductsEntry,Void,Void>
    {
        private ProductDao productDao;
        private updateProductAsynctask(ProductDao productDao)
        {
            this.productDao = productDao;
        }
        @Override
        protected Void doInBackground(ProductsEntry... productsEntries) {

            productDao.update(productsEntries[0]);

            return null;
        }
    }

    public static class deletProductAsynctask extends AsyncTask<ProductsEntry,Void,Void>
    {
        private ProductDao productDao;
        private deletProductAsynctask(ProductDao productDao)
        {
            this.productDao = productDao;
        }
        @Override
        protected Void doInBackground(ProductsEntry... productsEntries) {

            productDao.delet(productsEntries[0]);

            return null;
        }
    }

    public static class deletAllProductAsynctask extends AsyncTask<Void,Void,Void>
    {
        private ProductDao productDao;
        private deletAllProductAsynctask(ProductDao productDao)
        {
            this.productDao = productDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {

            productDao.deletAllProducts();

            return null;
        }
    }



    /**
     * this methods is for Product functions insert,delete,update,delete all, get all products
     *
     * @param billsEntry
     */
    public void insert(BillsEntry billsEntry)
    {
        new insertBillAsynctask(billDao).execute(billsEntry);
    }

    public void delete(BillsEntry billsEntry)
    {
        new deleteBillAsynctask(billDao).execute(billsEntry);
    }

    public void update(BillsEntry billsEntry)
    {
        new updateBillAsynctask(billDao).execute(billsEntry);
    }

    public void deleteAllBills()
    {
        new deleteAllBillAsynctask(billDao).execute();
    }


    public LiveData<List<BillsEntry>> getAllBills()
    {
        return allBills;
    }




    public BillsEntry getbillItemsAt(int id)
    {
        getBillIAtAsynctask billAtAsynctask = new getBillIAtAsynctask(billDao);



        billAtAsynctask.execute(id);

        return billAtAsynctask.getBillItemsById();
    }

    public LiveData<List<BillsItemEntry>> getAllBillsItemAtId(int _id)
    {
      return   billItemDao.getbillItemsAt(_id);
    }



    public static class getBillIAtAsynctask extends AsyncTask<Integer,Void,Void>
    {
        private BillDao billDao;
        private BillsEntry billsEntry;
        public getBillIAtAsynctask(BillDao billDao)
        {
            this.billDao = billDao;
        }


        @Override
        protected Void doInBackground(Integer... integers) {
           LiveData<List<BillsEntry>> liveData =  billDao.getbillAt(integers[0]);

           billsEntry = liveData.getValue().get(0);

            return null;
        }


        public BillsEntry getBillItemsById()
        {

            return billsEntry;
        }


    }

    /**
     *  we cant do database operations in the main thread so we do it in the background thread
     *  for bills database functions insert,delete,update,delete all, get all products
     */
    public static class insertBillAsynctask extends AsyncTask<BillsEntry,Void,Void>
    {
        private BillDao billDao;
        private insertBillAsynctask(BillDao billDao)
        {
            this.billDao = billDao;
        }

        @Override
        protected Void doInBackground(BillsEntry... billsEntries) {

            billDao.insert(billsEntries[0]);

            return null;
        }
    }

    public static class updateBillAsynctask extends AsyncTask<BillsEntry,Void,Void>
    {
        private BillDao billDao;
        private updateBillAsynctask(BillDao billDao)
        {
            this.billDao = billDao;
        }

        @Override
        protected Void doInBackground(BillsEntry... billsEntries) {

            billDao.update(billsEntries[0]);

            return null;
        }
    }

    public static class deleteBillAsynctask extends AsyncTask<BillsEntry,Void,Void>
    {
        private BillDao billDao;
        private deleteBillAsynctask(BillDao billDao)
        {
            this.billDao = billDao;
        }

        @Override
        protected Void doInBackground(BillsEntry... billsEntries) {

            billDao.delet(billsEntries[0]);

            return null;
        }
    }

    public static class deleteAllBillAsynctask extends AsyncTask<Void,Void,Void>
    {
        private BillDao billDao;
        private deleteAllBillAsynctask(BillDao billDao)
        {
            this.billDao = billDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            billDao.deletAllBills();
            return null;
        }
    }

    /**
     * this methods is for bills functions insert,delete,update,delete all, get all products
     *
     * @param billsItemEntry
     */
    public void insert(BillsItemEntry billsItemEntry)
    {
        new insertBillItemAsynctask(billItemDao).execute(billsItemEntry);
    }

    public void delete(BillsItemEntry billsItemEntry)
    {
        new deleteBillItemAsynctask(billItemDao).execute(billsItemEntry);
    }

    public void update(BillsItemEntry billsItemEntry)
    {
        new updateBillItemAsynctask(billItemDao).execute(billsItemEntry);
    }

    public void deleteAllBillItems()
    {
        new deleteAllBillItemAsynctask(billItemDao).execute();
    }

    public LiveData<List<BillsItemEntry>> getAllBillItems()
    {
        return allBillsItem;
    }





    /**
     *  we cant do database operations in the main thread so we do it in the background thread
     *  for product database functions insert,delete,update,delete all, get all products
     */
    public static class insertBillItemAsynctask extends AsyncTask<BillsItemEntry,Void,Void>
    {
        private BillItemDao billItemDao;
        private insertBillItemAsynctask(BillItemDao billItemDao)
        {
            this.billItemDao = billItemDao;
        }

        @Override
        protected Void doInBackground(BillsItemEntry... billsItemEntries) {

            billItemDao.insert(billsItemEntries[0]);

            return null;
        }
    }



    public static class deleteBillItemAsynctask extends AsyncTask<BillsItemEntry,Void,Void>
    {
        private BillItemDao billItemDao;
        private deleteBillItemAsynctask(BillItemDao billItemDao)
        {
            this.billItemDao = billItemDao;
        }

        @Override
        protected Void doInBackground(BillsItemEntry... billsItemEntries) {

            billItemDao.delet(billsItemEntries[0]);

            return null;
        }
    }
    public static class updateBillItemAsynctask extends AsyncTask<BillsItemEntry,Void,Void>
    {
        private BillItemDao billItemDao;
        private updateBillItemAsynctask(BillItemDao billItemDao)
        {
            this.billItemDao = billItemDao;
        }

        @Override
        protected Void doInBackground(BillsItemEntry... billsItemEntries) {

            billItemDao.update(billsItemEntries[0]);

            return null;
        }
    }
    public static class deleteAllBillItemAsynctask extends AsyncTask<BillsItemEntry,Void,Void>
    {
        private BillItemDao billItemDao;
        private deleteAllBillItemAsynctask(BillItemDao billItemDao)
        {
            this.billItemDao = billItemDao;
        }

        @Override
        protected Void doInBackground(BillsItemEntry... billsItemEntries) {

            billItemDao.deletAllBills();

            return null;
        }
    }


    /**
     * this methods is for bills functions insert,delete,update,delete all, get all products
     *
     * @param logEntry
     */
    public void insert(LogEntry logEntry)
    {
        new insertLogAsynctask(logDao).execute(logEntry);
    }

    public void delete(LogEntry logEntry)
    {
        new deleteLogAsynctask(logDao).execute(logEntry);
    }

    public void update(LogEntry logEntry)
    {
        new updateLogAsynctask(logDao).execute(logEntry);
    }

    public void deleteAllLogsItems()
    {
        new deleteAllLogAsynctask(logDao).execute();
    }

    public LiveData<List<LogEntry>> getAllLogsItems()
    {
        return allLogs;
    }

    /**
     *  we cant do database operations in the main thread so we do it in the background thread
     *  for product database functions insert,delete,update,delete all, get all products
     */
    public static class insertLogAsynctask extends AsyncTask<LogEntry,Void,Void>
    {
        private LogDao logDao;
        private insertLogAsynctask(LogDao logDao)
        {
            this.logDao = logDao;
        }

        @Override
        protected Void doInBackground(LogEntry... LogEntry) {

            logDao.insert(LogEntry[0]);

            return null;
        }
    }

    public static class updateLogAsynctask extends AsyncTask<LogEntry,Void,Void>
    {
        private LogDao logDao;
        private updateLogAsynctask(LogDao logDao)
        {
            this.logDao = logDao;
        }

        @Override
        protected Void doInBackground(LogEntry... LogEntry) {

            logDao.update(LogEntry[0]);

            return null;
        }
    }

    public static class deleteLogAsynctask extends AsyncTask<LogEntry,Void,Void>
    {
        private LogDao logDao;
        private deleteLogAsynctask(LogDao logDao)
        {
            this.logDao = logDao;
        }

        @Override
        protected Void doInBackground(LogEntry... LogEntry) {

            logDao.delet(LogEntry[0]);

            return null;
        }
    }

    public static class deleteAllLogAsynctask extends AsyncTask<Void,Void,Void>
    {
        private LogDao logDao;
        private deleteAllLogAsynctask(LogDao logDao)
        {
            this.logDao = logDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            logDao.deletAllLogs();

            return null;
        }
    }



}
