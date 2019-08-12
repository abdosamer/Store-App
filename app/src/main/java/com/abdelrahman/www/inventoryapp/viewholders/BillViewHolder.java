package com.abdelrahman.www.inventoryapp.viewholders;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.abdelrahman.www.inventoryapp.BillsActivity;
import com.abdelrahman.www.inventoryapp.R;
import com.abdelrahman.www.inventoryapp.adapters.BillsAdapter;
import com.abdelrahman.www.inventoryapp.adapters.BillsItemAdapter;
import com.abdelrahman.www.inventoryapp.data.InventoryContract.BillsItemEntry;
import com.abdelrahman.www.inventoryapp.data.InventoryContract.BillsEntry;
import com.abdelrahman.www.inventoryapp.data.InventoryViewModel;

import java.util.List;

/**
 * the view holder for single product to attach the data to its coresponding views
 */
public class BillViewHolder extends RecyclerView.ViewHolder {

    TextView mDateView;
    TextView mTotalPriceView;
    CardView mViewHolder;

    RecyclerView productList;
    BillsItemAdapter mAdapter;

    InventoryViewModel inventoryViewModel;
    /**
     * attaching the views to the xml views
     */
    public BillViewHolder(View itemView) {
        super(itemView);


         mDateView = itemView.findViewById(R.id.bill_date);
         mTotalPriceView = itemView.findViewById(R.id.bill_total_price);
        productList = itemView.findViewById(R.id.bils_items_list);
        mViewHolder = itemView.findViewById(R.id.bill_view_holder);


    }

    /**
     * attaching the data to its views
     */
    public void onBindBillHolder(final BillsEntry billsEntry, final Context context,FragmentActivity fragmentActivity,LifecycleOwner lifecycleOwner) {
        //setting the name to its view

        mDateView.setText("No."+billsEntry.getCOLUMN_BILL_DATE());

        mTotalPriceView.setText(billsEntry.getCOLUMN_BILL_TOTAL_PRICE()+" $");


        /**
         *  refrencing the list that in the mainActivity.xml to use it in showing the products
         */
        productList = itemView.findViewById(R.id.bils_items_list);
        productList.setLayoutManager(new LinearLayoutManager(context));
        productList.setHasFixedSize(true);

        mAdapter = new BillsItemAdapter();


        /** attaching the adapter to our list to show all products */
        productList.setAdapter(mAdapter);


        /** getting the data from viewModel in onChange method so that we can keep listening for any change in the data
         *  so it will update the UI automatically :)
         */

        inventoryViewModel = ViewModelProviders.of(fragmentActivity).get(InventoryViewModel.class);

        inventoryViewModel.getAllBillsItemAtId(billsEntry.get_BID()).observe(lifecycleOwner, new Observer<List<BillsItemEntry>>() {
            @Override
            public void onChanged(@Nullable List<BillsItemEntry> billsItemEntries) {
                // update the rcyclerView

                mAdapter.submitList(billsItemEntries);
            }
        });



        mViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveteacheruid(billsEntry.get_BID(),billsEntry.getCOLUMN_BILL_DATE(),billsEntry.getCOLUMN_BILL_TOTAL_PRICE(),context);


                Toast.makeText(context, "current Bill is "+billsEntry.getCOLUMN_BILL_DATE(), Toast.LENGTH_SHORT).show();
            }
        });


    }




    void saveteacheruid(int uid,long date,double total_price,Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("bill_uid", uid);
        editor.putLong("bill_date", uid);
        editor.putString("bill_total_price", String.valueOf(total_price));

        editor.apply();
    }


}
