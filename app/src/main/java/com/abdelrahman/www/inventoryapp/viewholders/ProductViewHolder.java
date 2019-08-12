package com.abdelrahman.www.inventoryapp.viewholders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.abdelrahman.www.inventoryapp.R;
import com.abdelrahman.www.inventoryapp.data.InventoryContract.ProductsEntry;

/**
 * the view holder for single product to attach the data to its coresponding views
 */
public class ProductViewHolder extends RecyclerView.ViewHolder {

    TextView mNameView;
    TextView mPriceView;
    TextView mPicView;
    TextView mQuantityView;
    TextView mSellView;
    CardView productHolder;

    /**
     * attaching the views to the xml views
     */
    public ProductViewHolder(View itemView) {
        super(itemView);
         mNameView = itemView.findViewById(R.id.product_item_name);
         mPriceView = itemView.findViewById(R.id.product_item_price);
         mPicView = itemView.findViewById(R.id.product_item_pic_view);
         mQuantityView = itemView.findViewById(R.id.product_item_quantity);
         mSellView = itemView.findViewById(R.id.product_item_sell_button);
        productHolder = itemView.findViewById(R.id.product_view_holder);

    }

    /**
     *  attaching the data to its views
     */
    public void onBindProductHolder(ProductsEntry product, Context context, View.OnClickListener holderListeaner)
    {
        //setting the name to its view
        mNameView.setText(product.getCOLUMN_PRODUCT_NAME());


        //setting the price to its view
        //note: we used String.valueof() to convert double to strinf the reurned value is double)
        mPriceView.setText(String.valueOf(product.getCOLUMN_PRODUCT_PRICE())+" $");



        int limitColor = ContextCompat.getColor(context,R.color.colorAccent);

        int usualColor = ContextCompat.getColor(context,R.color.lits_item_title_color);



        //checking if the quantity is under limit so will sit its color to be red
        if(product.getCOLUMN_PRODUCT_QUANTITY()<=product.getCOLUMN_PRODUCT_QUANTITY_LIMIT() )
        {
            mQuantityView.setTextColor(limitColor);
        }else{
            mQuantityView.setTextColor(usualColor);
        }
        //setting the quantity to its view
        mQuantityView.setText(String.valueOf(product.getCOLUMN_PRODUCT_QUANTITY()));

        //geeting the first letter of the name
        char[] picLetter = product.getCOLUMN_PRODUCT_NAME().toCharArray();

        // setting the first letter to the pic of the view that its a text view too

        mPicView.setText(picLetter[0]+"");

        mPicView.setAllCaps(true);


        //setting a listeaner if the product clicked
        productHolder.setOnClickListener(holderListeaner);
    }

}
