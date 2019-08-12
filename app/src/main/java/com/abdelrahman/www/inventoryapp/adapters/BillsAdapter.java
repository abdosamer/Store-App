package com.abdelrahman.www.inventoryapp.adapters;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdelrahman.www.inventoryapp.R;
import com.abdelrahman.www.inventoryapp.data.InventoryContract.BillsEntry;
import com.abdelrahman.www.inventoryapp.viewholders.BillViewHolder;

public class BillsAdapter extends ListAdapter<BillsEntry,BillViewHolder> {

    Context context;
    FragmentActivity fragmentActivity;
    LifecycleOwner lifecycleOwner;
    public BillsAdapter(final Context context, FragmentActivity fragmentActivity, LifecycleOwner lifecycleOwner) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.fragmentActivity = fragmentActivity;
        this.lifecycleOwner = lifecycleOwner;
    }

    private static final DiffUtil.ItemCallback<BillsEntry> DIFF_CALLBACK = new DiffUtil.ItemCallback<BillsEntry>() {
        @Override
        public boolean areItemsTheSame(BillsEntry oldItem, BillsEntry newItem) {
            return oldItem.get_BID() == newItem.get_BID();
        }

        @Override
        public boolean areContentsTheSame(BillsEntry oldItem, BillsEntry newItem) {
            return false;
        }


    };
    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_list_item,parent,false);

        return new BillViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {

        BillsEntry currentBill = getItem(position);

        holder.onBindBillHolder(currentBill,context,fragmentActivity,lifecycleOwner);
    }

}
