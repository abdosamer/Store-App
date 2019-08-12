package com.abdelrahman.www.inventoryapp.viewholders;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.abdelrahman.www.inventoryapp.R;
import com.abdelrahman.www.inventoryapp.data.InventoryContract.BillsItemEntry;

/**
 * the view holder for single product to attach the data to its coresponding views
 */
public class BillItemViewHolder extends RecyclerView.ViewHolder {

    TextView mTitleView;
    TextView mQuantityView;
    TextView mPPriceView;
    TextView mTotalPriceView;
    /**
     * attaching the views to the xml views
     */

    public BillItemViewHolder(View itemView) {
        super(itemView);


        mQuantityView = itemView.findViewById(R.id.bill_product_quantity);
         mTotalPriceView = itemView.findViewById(R.id.bill_product_total_price);
         mTitleView = itemView.findViewById(R.id.bill_product_name);
         mPPriceView = itemView.findViewById(R.id.bill_product_price);


    }

    /**
     * attaching the data to its views
     */
    public void onBindBillItemHolder(BillsItemEntry billsItemEntry) {
        //setting the name to its view

       mQuantityView.setText(String.valueOf(billsItemEntry.getCOLUMN_BILL_ITEM_QUANTITY()));

       mTitleView.setText(billsItemEntry.get_PRODUCT_NAME());

       mPPriceView.setText(billsItemEntry.getCOLUMN_BILL_ITEM_PRICE());

       mTotalPriceView.setText(String.valueOf(billsItemEntry.getCOLUMN_BILL_ITEM_TOTAL_PRICE()));


    }






}
