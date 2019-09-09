package com.abdelrahman.www.inventoryapp.company;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.abdelrahman.www.inventoryapp.MainActivity;
import com.abdelrahman.www.inventoryapp.R;

public class EditorActivity extends AppCompatActivity {

    public static final String EDITOR_ACTIVITY = "EDITOR_ACTIVITY";
    public static final int ACTIVITY_EDIT_TAG = 1;
    public static final int ACTIVITY_EDIT_DELETE_TAG = 3;
    public static final int ACTIVITY_ADD_TAG = 2;
    public static final String PRODUCT_ID_KEY = "_id";
    public static final String PRODUCT_NAME_KEY = "product_name";
    public static final String PRODUCT_PRICE_KEY = "product_price";
    public static final String PRODUCT_CODE_KEY = "product_code";
    public static final String PRODUCT_QUANTITY_KEY = "product_quamtity";
    public static final String PRODUCT_QUANTITY_DIFF_KEY = "product_quamtity_diff";
    public static final String PRODUCT_QUANTITY_LIMIT_KEY = "product_quantity_limit";

    // the id holer if we are editng an existing product
    private int _id = -1;
    TextView nameView;
    TextView priceView;
    TextView codeView;
    NumberPicker quantityView;
    NumberPicker quantityLimitView;

    public static int quantityOld;

    public static  int activityIdentifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        //making the back button work in the actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameView = findViewById(R.id.edit_product_name);
        priceView = findViewById(R.id.edit_product_price);
        codeView = findViewById(R.id.edit_product_code);

        quantityView = findViewById(R.id.product_quantity);

        quantityView.setMinValue(0);
        quantityView.setMaxValue(2000);

        quantityLimitView = findViewById(R.id.product_quantity_limit);

        quantityLimitView.setMinValue(0);
        quantityLimitView.setMaxValue(500);



        Intent intent = getIntent();

         activityIdentifier = intent.getIntExtra(EDITOR_ACTIVITY,0);

        if(activityIdentifier == ACTIVITY_ADD_TAG)
        {
            // setting the title to be title of the activity to be add Product
            setTitle(getString(R.string.add_product));

        }else if (activityIdentifier == ACTIVITY_EDIT_TAG)
        {
            // setting the title to be title of the activity to be Edit Product
            setTitle(getString(R.string.edit_product));

            /**
             * setting the product data that we got from the intent to the
             *  corresponding view to show it to the user
             */

            nameView.setText(intent.getStringExtra(PRODUCT_NAME_KEY));
            priceView.setText(String.valueOf(intent.getDoubleExtra(PRODUCT_PRICE_KEY,0.0)));
            codeView.setText(String.valueOf(intent.getIntExtra(PRODUCT_CODE_KEY,0)));
            quantityView.setValue(intent.getIntExtra(PRODUCT_QUANTITY_KEY,0));
            quantityLimitView.setValue(intent.getIntExtra(PRODUCT_QUANTITY_LIMIT_KEY,0));
            _id = intent.getIntExtra(PRODUCT_ID_KEY,-1 );
            quantityOld = intent.getIntExtra(PRODUCT_QUANTITY_KEY,0);

        }





    }


    private boolean isFormvalid()
    {
        boolean result = true;

        if(TextUtils.isEmpty(nameView.getText().toString()))
        {
            nameView.setError(getString(R.string.this_field_is_required));
            result = false;
        }else
        {
            nameView.setError(null);
        }

        if(TextUtils.isEmpty(priceView.getText().toString()))
        {
            priceView.setError(getString(R.string.this_field_is_required));
            result = false;
        }else
        {
            priceView.setError(null);
        }

        if(TextUtils.isEmpty(codeView.getText().toString()))
        {
            priceView.setError(getString(R.string.this_field_is_required));
            result = false;
        }else
        {
            priceView.setError(null);
        }


        if(quantityView.getValue() <= 0)
        {
            priceView.setError(getString(R.string.this_field_cant_be_zero));
            result = false;
        }else
        {
            priceView.setError(null);
        }

        if(quantityLimitView.getValue() <= 0)
        {
            priceView.setError(getString(R.string.this_field_cant_be_zero));
            result = false;
        }else
        {
            priceView.setError(null);
        }

        return result;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
      MenuItem menuItem =  menu.findItem(R.id.action_delete);

        if(activityIdentifier == ACTIVITY_ADD_TAG)
        {
            menuItem.setVisible(false);
        }
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:

                /**
                 * save the product
                 */
            saveProduct();

                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:

                deletProduct();

                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                    onBackPressed();

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void saveProduct() {

        if(!isFormvalid())
        {
            return;
        }


        Intent intent = new Intent(EditorActivity.this,MainActivity.class);


        intent.putExtra(PRODUCT_NAME_KEY,nameView.getText().toString());
        intent.putExtra(PRODUCT_PRICE_KEY,Double.parseDouble(priceView.getText().toString()));
        intent.putExtra(PRODUCT_CODE_KEY,Integer.parseInt(codeView.getText().toString()));
        intent.putExtra(PRODUCT_QUANTITY_KEY,quantityView.getValue());
        intent.putExtra(PRODUCT_QUANTITY_LIMIT_KEY,quantityLimitView.getValue());

        if (_id != -1)
        {
            intent.putExtra(PRODUCT_ID_KEY,_id);

            int quantityDiff = quantityView.getValue() - quantityOld;
            intent.putExtra(PRODUCT_QUANTITY_DIFF_KEY,quantityDiff);


            setResult(ACTIVITY_EDIT_TAG,intent);
        }else
        {
            setResult(ACTIVITY_ADD_TAG,intent);
        }
        finish();

    }

    private void deletProduct() {

        if(!isFormvalid())
        {
            return;
        }


        Intent intent = new Intent(EditorActivity.this,MainActivity.class);


        intent.putExtra(PRODUCT_NAME_KEY,nameView.getText().toString());
        intent.putExtra(PRODUCT_PRICE_KEY,Double.parseDouble(priceView.getText().toString()));
        intent.putExtra(PRODUCT_CODE_KEY,Integer.parseInt(codeView.getText().toString()));
        intent.putExtra(PRODUCT_QUANTITY_KEY,quantityView.getValue());
        intent.putExtra(PRODUCT_QUANTITY_LIMIT_KEY,quantityLimitView.getValue());

        if (_id != -1)
        {
            intent.putExtra(PRODUCT_ID_KEY,_id);
            setResult(ACTIVITY_EDIT_DELETE_TAG,intent);

        }else
        {
            setResult(ACTIVITY_ADD_TAG,intent);
        }
        finish();

    }





}
