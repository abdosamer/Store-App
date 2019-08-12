package com.abdelrahman.www.inventoryapp.data;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


import com.abdelrahman.www.inventoryapp.data.InventoryContract.*;
import com.abdelrahman.www.inventoryapp.data.doas.BillDao;
import com.abdelrahman.www.inventoryapp.data.doas.BillItemDao;
import com.abdelrahman.www.inventoryapp.data.doas.LogDao;
import com.abdelrahman.www.inventoryapp.data.doas.ProductDao;


@Database(entities = {ProductsEntry.class,LogEntry.class,BillsEntry.class,BillsItemEntry.class},version = 11)
public abstract class InventoryDataBase extends RoomDatabase {


    private static InventoryDataBase instance;


    public abstract ProductDao productDao();

    public abstract BillDao billDao();

    public abstract BillItemDao billItemDao();

    public abstract LogDao logDao();



    public static synchronized InventoryDataBase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    InventoryDataBase.class, "inventory_table")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();

        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

   private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>
   {
        private ProductDao productDao;

        private PopulateDbAsyncTask(InventoryDataBase dataBase)
        {
            productDao = dataBase.productDao();
        }

       @Override
       protected Void doInBackground(Void... voids) {

            /**  we here insert 3 dummy products to test out list and functionality */
           productDao.insert(new ProductsEntry("lamp venos",12.5,
                   12345678,10,3));
           productDao.insert(new ProductsEntry("fisher",13.0,
                   12345678,15,3));

           return null;
       }
   }


}
