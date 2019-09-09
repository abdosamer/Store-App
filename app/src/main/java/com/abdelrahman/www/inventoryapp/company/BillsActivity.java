package com.abdelrahman.www.inventoryapp.company;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.abdelrahman.www.inventoryapp.R;
import com.abdelrahman.www.inventoryapp.adapters.BillsAdapter;
import com.abdelrahman.www.inventoryapp.adapters.LogAdapter;
import com.abdelrahman.www.inventoryapp.data.InventoryContract.BillsEntry;
import com.abdelrahman.www.inventoryapp.data.InventoryViewModel;

import java.util.Collections;
import java.util.List;

public class BillsActivity extends AppCompatActivity {



    // activity identifier
    public static final int ACTIVITY_BILLS = 6;
    BillsAdapter mAdapter;
    InventoryViewModel inventoryViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);


        /**
         *  refrencing the list that in the mainActivity.xml to use it in showing the products
         */
        RecyclerView productsListView = findViewById(R.id.bills_list);
        productsListView.setLayoutManager(new LinearLayoutManager(this));
        productsListView.setHasFixedSize(true);

        mAdapter = new BillsAdapter(this,this,this);


        /** attaching the adapter to our list to show all products */
        productsListView.setAdapter(mAdapter);


        /** getting the data from viewModel in onChange method so that we can keep listening for any change in the data
         *  so it will update the UI automatically :)
         */



        inventoryViewModel = ViewModelProviders.of(this).get(InventoryViewModel.class);

        inventoryViewModel.getAllBills().observe(this, new Observer<List<BillsEntry>>() {
            @Override
            public void onChanged(@Nullable List<BillsEntry> billsEntries) {
                // update the rcyclerView

                Collections.reverse(billsEntries);
                mAdapter.submitList(billsEntries);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_bills, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_add_bill:

                /**
                 * save the product
                 */
                createNewBill();

                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete_all_bills:

                inventoryViewModel.deletAllBills();

                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                onBackPressed();

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void createNewBill() {

         long date =   System.currentTimeMillis();
        BillsEntry billsEntry = new BillsEntry(date,0.0);

        inventoryViewModel.insert(billsEntry);
    }


}
