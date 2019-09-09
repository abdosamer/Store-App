package com.abdelrahman.www.inventoryapp;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.abdelrahman.www.inventoryapp.adapters.ProductAdapter;
import com.abdelrahman.www.inventoryapp.company.BillsActivity;
import com.abdelrahman.www.inventoryapp.company.EditorActivity;
import com.abdelrahman.www.inventoryapp.company.LogsActivity;
import com.abdelrahman.www.inventoryapp.data.InventoryContract;
import com.abdelrahman.www.inventoryapp.data.InventoryViewModel;
import com.abdelrahman.www.inventoryapp.data.InventoryContract.*;

import java.util.Collections;
import java.util.List;

import static com.abdelrahman.www.inventoryapp.company.EditorActivity.ACTIVITY_ADD_TAG;
import static com.abdelrahman.www.inventoryapp.company.EditorActivity.ACTIVITY_EDIT_DELETE_TAG;
import static com.abdelrahman.www.inventoryapp.company.EditorActivity.ACTIVITY_EDIT_TAG;
import static com.abdelrahman.www.inventoryapp.company.EditorActivity.EDITOR_ACTIVITY;
import static com.abdelrahman.www.inventoryapp.company.EditorActivity.PRODUCT_CODE_KEY;
import static com.abdelrahman.www.inventoryapp.company.EditorActivity.PRODUCT_ID_KEY;
import static com.abdelrahman.www.inventoryapp.company.EditorActivity.PRODUCT_NAME_KEY;
import static com.abdelrahman.www.inventoryapp.company.EditorActivity.PRODUCT_PRICE_KEY;
import static com.abdelrahman.www.inventoryapp.company.EditorActivity.PRODUCT_QUANTITY_DIFF_KEY;
import static com.abdelrahman.www.inventoryapp.company.EditorActivity.PRODUCT_QUANTITY_KEY;
import static com.abdelrahman.www.inventoryapp.company.EditorActivity.PRODUCT_QUANTITY_LIMIT_KEY;


public class MainActivity extends AppCompatActivity {

    /**
     * declaring the list products adapter
     */
    ProductAdapter mAdapter;
    InventoryViewModel inventoryViewModel;

    boolean doItOnce;
    double mQuantity;
    double mPrice;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /** refrencing the list that in the mainActivity.xml to use it in showing the products
         */
        RecyclerView productsListView = findViewById(R.id.products_list);
        productsListView.setLayoutManager(new LinearLayoutManager(this));
        productsListView.setHasFixedSize(true);

        mAdapter = new ProductAdapter(this);


        /** attaching the adapter to our list to show all products */
        productsListView.setAdapter(mAdapter);


        /** getting the data from viewModel in onChange method so that we can keep listening for any change in the data
         *  so it will update the UI automatically :)
         */

        inventoryViewModel = ViewModelProviders.of(this).get(InventoryViewModel.class);

        inventoryViewModel.getAllProducts().observe(this, new Observer<List<ProductsEntry>>() {
            @Override
            public void onChanged(@Nullable List<ProductsEntry> productsEntries) {
                // update the rcyclerView

                Collections.reverse(productsEntries);
                mAdapter.submitList(productsEntries);
            }
        });


        /**
         * get the item liseaner that we created then make it open editor activity if the item clicked
         */

        mAdapter.setOnProductClickListeaner(new ProductAdapter.onProductClick() {
            @Override
            public void onProductClick(ProductsEntry product) {

                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra(PRODUCT_NAME_KEY, product.getCOLUMN_PRODUCT_NAME());
                intent.putExtra(PRODUCT_PRICE_KEY, product.getCOLUMN_PRODUCT_PRICE());
                intent.putExtra(PRODUCT_CODE_KEY, product.getCOLUMN_PRODUCT_CODE());
                intent.putExtra(PRODUCT_QUANTITY_KEY, product.getCOLUMN_PRODUCT_QUANTITY());
                intent.putExtra(PRODUCT_QUANTITY_LIMIT_KEY, product.getCOLUMN_PRODUCT_QUANTITY_LIMIT());
                intent.putExtra(PRODUCT_ID_KEY, product.get_ID());
                intent.putExtra(EDITOR_ACTIVITY, ACTIVITY_EDIT_TAG);

                startActivityForResult(intent, ACTIVITY_EDIT_TAG);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                ProductsEntry currentProduct = mAdapter.getProductAt(viewHolder.getAdapterPosition());

                /** reducing the quantity by one
                 *   selling product
                 */


                int quantity = currentProduct.getCOLUMN_PRODUCT_QUANTITY();
                int _id = currentProduct.get_ID();
                currentProduct = new ProductsEntry(currentProduct.getCOLUMN_PRODUCT_NAME(),
                        currentProduct.getCOLUMN_PRODUCT_PRICE(), currentProduct.getCOLUMN_PRODUCT_CODE()
                        , (quantity - 1), currentProduct.getCOLUMN_PRODUCT_QUANTITY_LIMIT());

                currentProduct.set_ID(_id);

                butIntoBill(currentProduct.getCOLUMN_PRODUCT_NAME(), currentProduct.getCOLUMN_PRODUCT_PRICE(), 1);
                //logging all the selling operations and adding product operations
                LogEntry logEntry = new LogEntry(currentProduct.getCOLUMN_PRODUCT_NAME(), InventoryContract.PROFIT, currentProduct.getCOLUMN_PRODUCT_PRICE());


                inventoryViewModel.update(currentProduct);
                inventoryViewModel.insert(logEntry);
                Toast.makeText(MainActivity.this, "Note updated", Toast.LENGTH_SHORT).show();
            }

            private void butIntoBill(String name, double price, int quantity) {


                BillsEntry billsEntry = getCurrentBillId();

                if (billsEntry.get_BID() != -1) {





                    BillsItemEntry billsItemEntry = new BillsItemEntry(billsEntry.get_BID(),name,quantity,String.valueOf(price),price*quantity);

                    updateOrInsertBillItem(billsEntry.get_BID(),billsItemEntry,price);


                    saveUpdatedBillData(billsEntry,price);

                               }


            }
        }).attachToRecyclerView(productsListView);
    }

    private void updateOrInsertBillItem(int _bid, final BillsItemEntry billsItemEntryOld, final double price) {

        inventoryViewModel = ViewModelProviders.of(this).get(InventoryViewModel.class);

         doItOnce = true;
        inventoryViewModel.getAllBillsItemAtId(_bid).observe(this, new Observer<List<BillsItemEntry>>() {
            @Override
            public void onChanged(@Nullable List<BillsItemEntry> billsItemEntries) {
                // update the rcyclerView
                if (doItOnce)
                {
                    boolean isItOld = false;
                int item_id = -1;
                for (int x = 0; x < billsItemEntries.size(); x++) {
                    if (billsItemEntries.get(x).get_PRODUCT_NAME().contentEquals(billsItemEntryOld.get_PRODUCT_NAME())) {
                        isItOld = true;
                        item_id = billsItemEntries.get(x).get_ID();
                        mPrice = billsItemEntries.get(x).getCOLUMN_BILL_ITEM_TOTAL_PRICE();
                        mQuantity  = billsItemEntries.get(x).getCOLUMN_BILL_ITEM_QUANTITY();

                    }
                }

                if (isItOld) {
                    BillsItemEntry billsItemEntryNew = new BillsItemEntry(billsItemEntryOld.get_BID(), billsItemEntryOld.get_PRODUCT_NAME(),
                            mQuantity+ 1, billsItemEntryOld.getCOLUMN_BILL_ITEM_PRICE()
                            , mPrice + price);

                    billsItemEntryNew.set_ID(item_id);

                    inventoryViewModel.update(billsItemEntryNew);
                } else {
                    inventoryViewModel.insert(billsItemEntryOld);
                }
                doItOnce = false;
            }

            }
        });




    }

    private void saveUpdatedBillData(BillsEntry billsEntryOld, double price) {


        BillsEntry billsEntryNew = new BillsEntry(billsEntryOld.getCOLUMN_BILL_DATE(),billsEntryOld.getCOLUMN_BILL_TOTAL_PRICE()+price);

        billsEntryNew.set_BID(billsEntryOld.get_BID());

        inventoryViewModel.update(billsEntryNew);

        savebill(billsEntryNew.get_BID(),billsEntryNew.getCOLUMN_BILL_DATE(),billsEntryNew.getCOLUMN_BILL_TOTAL_PRICE()
                ,MainActivity.this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_add_product:

                /**
                 * open the editor activity to add new pet and wait for results
                 */

                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra(EDITOR_ACTIVITY, ACTIVITY_ADD_TAG);

                startActivityForResult(intent, ACTIVITY_ADD_TAG);

                return true;
            // Respond to a click on the "open logs" menu option
            case R.id.action_open_logs_activity:

                startActivity(new Intent(MainActivity.this, LogsActivity.class));
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_open_bills_activity:
                startActivity(new Intent(MainActivity.this, BillsActivity.class));


                return true;
            case R.id.action_delete_all_product:

                inventoryViewModel.deletAll();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // if we called to add product
        if (requestCode == ACTIVITY_ADD_TAG) {


            if (resultCode == ACTIVITY_ADD_TAG) {
                // if we get a new product
                insertProduct(data);
                Toast.makeText(this, "product saved", Toast.LENGTH_SHORT).show();
            } else {
                //if we get any new product "user click back"
                Toast.makeText(this, "product not saved ", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == ACTIVITY_EDIT_TAG) {
            if (resultCode == ACTIVITY_EDIT_TAG) {
                updateProduct(data);
                Toast.makeText(this, "product updated", Toast.LENGTH_SHORT).show();
            } else if (resultCode == ACTIVITY_EDIT_DELETE_TAG) {
                deletProduct(data);
                Toast.makeText(this, "product deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "product not updated", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void updateProduct(Intent data) {
        String name = data.getStringExtra(PRODUCT_NAME_KEY);
        double price = data.getDoubleExtra(PRODUCT_PRICE_KEY, 0.0);
        int code = data.getIntExtra(PRODUCT_CODE_KEY, 0);
        int quantity = data.getIntExtra(PRODUCT_QUANTITY_KEY, 0);
        int quantityDiff = data.getIntExtra(PRODUCT_QUANTITY_DIFF_KEY, 0);
        int quantityLimit = data.getIntExtra(PRODUCT_QUANTITY_LIMIT_KEY, 0);
        int _id = data.getIntExtra(PRODUCT_ID_KEY, -1);

        ProductsEntry productsEntry = new ProductsEntry(name, price
                , code, quantity, quantityLimit);

        //logging all the selling operations and adding product operations

        if (quantityDiff > 0) {
            String note = getString(R.string.added);
            LogEntry logEntry = new LogEntry(name + " " + note, InventoryContract.PROFIT, quantityDiff * price);
            inventoryViewModel.insert(logEntry);
        } else if (quantityDiff < 0) {
            String note = getString(R.string.losies);
            LogEntry logEntry = new LogEntry(name + " " + note, InventoryContract.LOSS, quantityDiff * price);
            inventoryViewModel.insert(logEntry);
        }


        productsEntry.set_ID(_id);
        inventoryViewModel.update(productsEntry);

    }

    private void deletProduct(Intent data) {
        String name = data.getStringExtra(PRODUCT_NAME_KEY);
        double price = data.getDoubleExtra(PRODUCT_PRICE_KEY, 0.0);
        int code = data.getIntExtra(PRODUCT_CODE_KEY, 0);
        int quantity = data.getIntExtra(PRODUCT_QUANTITY_KEY, 0);
        int quantityLimit = data.getIntExtra(PRODUCT_QUANTITY_LIMIT_KEY, 0);
        int _id = data.getIntExtra(PRODUCT_ID_KEY, -1);

        ProductsEntry productsEntry = new ProductsEntry(name, price
                , code, quantity, quantityLimit);
        productsEntry.set_ID(_id);
        inventoryViewModel.delet(productsEntry);

    }

    private void insertProduct(Intent data) {
        String name = data.getStringExtra(PRODUCT_NAME_KEY);
        double price = data.getDoubleExtra(PRODUCT_PRICE_KEY, 0.0);
        int code = data.getIntExtra(PRODUCT_CODE_KEY, 0);
        int quantity = data.getIntExtra(PRODUCT_QUANTITY_KEY, 0);
        int quantityLimit = data.getIntExtra(PRODUCT_QUANTITY_LIMIT_KEY, 0);

        ProductsEntry productsEntry = new ProductsEntry(name, price
                , code, quantity, quantityLimit);

        //logging all the selling operations and adding product operations
        LogEntry logEntry = new LogEntry(name +"sup. "+quantity , InventoryContract.PROFIT, price);

        inventoryViewModel.insert(productsEntry);
        inventoryViewModel.insert(logEntry);

    }


    public BillsEntry getCurrentBillId() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int uid = preferences.getInt(getString(R.string.bill_uid), -1);
        long date = preferences.getLong(getString(R.string.bill_date), -1);
        String totaPrice = preferences.getString(getString(R.string.bill_total_price), "0");

        BillsEntry billsEntry = new BillsEntry(date,Double.parseDouble(totaPrice));
        billsEntry.set_BID(uid);
        return billsEntry;
    }





    public static void savebill(int uid, long date, double total_price, Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("bill_uid", uid);
        editor.putLong("bill_date", date);
        editor.putString("bill_total_price", String.valueOf(total_price));

        editor.apply();
    }



}
