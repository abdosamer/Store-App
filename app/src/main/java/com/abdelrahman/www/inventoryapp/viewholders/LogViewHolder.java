package com.abdelrahman.www.inventoryapp.viewholders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.abdelrahman.www.inventoryapp.R;
import com.abdelrahman.www.inventoryapp.data.InventoryContract;
import com.abdelrahman.www.inventoryapp.data.InventoryContract.LogEntry;

/**
 * the view holder for single product to attach the data to its coresponding views
 */
public class LogViewHolder extends RecyclerView.ViewHolder {

    TextView mNameView;
    TextView mPriceView;
    TextView mTypeView;

    /**
     * attaching the views to the xml views
     */
    public LogViewHolder(View itemView) {
        super(itemView);
        mNameView = itemView.findViewById(R.id.log_item_name);
        mPriceView = itemView.findViewById(R.id.log_item_price);
        mTypeView = itemView.findViewById(R.id.log_item_type);


    }

    /**
     * attaching the data to its views
     */
    public void onBindLogHolder(LogEntry logEntry,Context context) {
        //setting the name to its view
        mNameView.setText(logEntry.getCOLUMN_LOG_NAME());


        //setting the price to its view
        //note: we used String.valueof() to convert double to strinf the reurned value is double)
        mPriceView.setText(String.valueOf(logEntry.COLUMN_LOG_VALUE) + " $");

        int greenColor = ContextCompat.getColor(context,R.color.colorProfit);
        int redColor = ContextCompat.getColor(context,R.color.colorAccent);


        //setting the type to its view
        int type = logEntry.getCOLUMN_LOG_TYPE();
        if (logEntry.isTypeValid(logEntry.getCOLUMN_LOG_TYPE())) {

            if (type == InventoryContract.LOSS) {
                mTypeView.setText(R.string.loss);

                mTypeView.setTextColor(redColor);
            } else {
                mTypeView.setText(R.string.profit);
                mTypeView.setTextColor(greenColor);

            }
        }


    }

}
