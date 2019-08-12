package com.abdelrahman.www.inventoryapp.adapters;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdelrahman.www.inventoryapp.R;
import com.abdelrahman.www.inventoryapp.data.InventoryContract;
import com.abdelrahman.www.inventoryapp.data.InventoryContract.ProductsEntry;
import com.abdelrahman.www.inventoryapp.viewholders.ProductViewHolder;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ProductAdapter extends ListAdapter<ProductsEntry,ProductViewHolder> {



    onProductClick listeaner;
    Context context;
    public ProductAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<ProductsEntry> DIFF_CALLBACK = new DiffUtil.ItemCallback<ProductsEntry>() {
        @Override
        public boolean areItemsTheSame(ProductsEntry oldItem, ProductsEntry newItem) {
            return oldItem.get_ID() == newItem.get_ID();
        }

        @Override
        public boolean areContentsTheSame(ProductsEntry oldItem, ProductsEntry newItem) {
            //this is method i had created manually check it in the class itself
            return oldItem.productContentEquals(newItem);
        }
    };


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item,parent,false);


        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {

        final ProductsEntry currentProduct = getItem(position);

        holder.onBindProductHolder(currentProduct,context, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listeaner != null && position != RecyclerView.NO_POSITION)
                {
                    listeaner.onProductClick(currentProduct);
                }
            }
        });



    }

    public ProductsEntry getProductAt(int position)
    {
        return getItem(position);
    }



    public interface onProductClick
    {
        void onProductClick(ProductsEntry product);
    }

    public void setOnProductClickListeaner(onProductClick listeaner)
    {
        this.listeaner = listeaner;
    }




}
