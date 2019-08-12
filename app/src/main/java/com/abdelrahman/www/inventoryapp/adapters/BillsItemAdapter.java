package com.abdelrahman.www.inventoryapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdelrahman.www.inventoryapp.R;
import com.abdelrahman.www.inventoryapp.data.InventoryContract.BillsItemEntry;
import com.abdelrahman.www.inventoryapp.viewholders.BillItemViewHolder;

public class BillsItemAdapter extends ListAdapter<BillsItemEntry,BillItemViewHolder> {


    public BillsItemAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public BillItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bills_item_list_item,parent,false);


        return new BillItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BillItemViewHolder holder, int position) {

        BillsItemEntry currentBillItem = getItem(position);

        holder.onBindBillItemHolder(currentBillItem);
    }

    private static final DiffUtil.ItemCallback<BillsItemEntry> DIFF_CALLBACK = new DiffUtil.ItemCallback<BillsItemEntry>() {
        @Override
        public boolean areItemsTheSame(BillsItemEntry oldItem, BillsItemEntry newItem) {
            return oldItem.get_ID() == newItem.get_ID();
        }

        @Override
        public boolean areContentsTheSame(BillsItemEntry oldItem, BillsItemEntry newItem) {
            return false;
        }


    };



}
