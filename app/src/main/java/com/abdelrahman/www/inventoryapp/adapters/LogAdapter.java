package com.abdelrahman.www.inventoryapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdelrahman.www.inventoryapp.R;
import com.abdelrahman.www.inventoryapp.data.InventoryContract;
import com.abdelrahman.www.inventoryapp.data.InventoryContract.LogEntry;
import com.abdelrahman.www.inventoryapp.viewholders.LogViewHolder;

public class LogAdapter extends ListAdapter<LogEntry,LogViewHolder> {

    Context context;
    public LogAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<LogEntry> DIFF_CALLBACK = new DiffUtil.ItemCallback<LogEntry>() {
        @Override
        public boolean areItemsTheSame(LogEntry oldItem, LogEntry newItem) {
            return oldItem.get_LID() == newItem.get_LID();
        }

        @Override
        public boolean areContentsTheSame(LogEntry oldItem, LogEntry newItem) {
            return false;
        }


    };

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_list_item,parent,false);

        return new LogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {


        LogEntry currentLog = getItem(position);

        holder.onBindLogHolder(currentLog,context);

    }
}
