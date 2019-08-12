package com.abdelrahman.www.inventoryapp.data;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;


public final class InventoryContract {


    // to prevent others from making an instance from it accedentally
    // make the empty contractor private
    private InventoryContract() {
    }

    public static final int PROFIT = 1;
    public static final int LOSS = 0;

    @Entity(tableName = "products_table")
    public static class ProductsEntry implements BaseColumns {


        /**
         * Unique ID number for the products (only for use in the database table).
         * <p>
         * Type: INTEGER
         */

        @PrimaryKey(autoGenerate = true)
        public int _ID;


        /**
         * Name of the product.
         * <p>
         * Type: TEXT
         */
        private String COLUMN_PRODUCT_NAME = "name";

        /**
         * Price of the prduct
         * <p>
         * Type: DOUBLE
         */
        private double COLUMN_PRODUCT_PRICE;

        /**
         * code of the product
         * <p>
         * type: INT
         */

        private int COLUMN_PRODUCT_CODE;

        /**
         * quantity of the product
         * <p>
         * Type: INT
         */
        private int COLUMN_PRODUCT_QUANTITY;

        /**
         * quantity limit of the product that will make a warn if the quantity
         * reachs it
         * <p>
         * Type: INT
         */
        private int COLUMN_PRODUCT_QUANTITY_LIMIT;


        public ProductsEntry(String COLUMN_PRODUCT_NAME, double COLUMN_PRODUCT_PRICE, int COLUMN_PRODUCT_CODE, int COLUMN_PRODUCT_QUANTITY, int COLUMN_PRODUCT_QUANTITY_LIMIT) {
            this.COLUMN_PRODUCT_NAME = COLUMN_PRODUCT_NAME;
            this.COLUMN_PRODUCT_PRICE = COLUMN_PRODUCT_PRICE;
            this.COLUMN_PRODUCT_CODE = COLUMN_PRODUCT_CODE;
            this.COLUMN_PRODUCT_QUANTITY = COLUMN_PRODUCT_QUANTITY;
            this.COLUMN_PRODUCT_QUANTITY_LIMIT = COLUMN_PRODUCT_QUANTITY_LIMIT;
        }

        public int get_ID() {
            return _ID;
        }

        public void set_ID(int _ID) {
            this._ID = _ID;
        }

        public String getCOLUMN_PRODUCT_NAME() {
            return COLUMN_PRODUCT_NAME;
        }

        public double getCOLUMN_PRODUCT_PRICE() {
            return COLUMN_PRODUCT_PRICE;
        }

        public int getCOLUMN_PRODUCT_CODE() {
            return COLUMN_PRODUCT_CODE;
        }

        public int getCOLUMN_PRODUCT_QUANTITY() {
            return COLUMN_PRODUCT_QUANTITY;
        }

        public int getCOLUMN_PRODUCT_QUANTITY_LIMIT() {
            return COLUMN_PRODUCT_QUANTITY_LIMIT;
        }

        /**
         * comparing between two products to see if they are the same or there is a diff that means th
         * products ahve been updated
         *
         * @param newItem
         * @return
         */
        public boolean productContentEquals(ProductsEntry newItem) {
            boolean result = true;
            // check if the id is the same
            if (newItem.get_ID() != get_ID()) {
                result = false;
            }

            // check if the name is the same
            if (!newItem.getCOLUMN_PRODUCT_NAME().contentEquals(getCOLUMN_PRODUCT_NAME())) {
                result = false;
            }
            // check if the price is the same
            if (newItem.getCOLUMN_PRODUCT_PRICE() != getCOLUMN_PRODUCT_PRICE()) {
                result = false;
            }
            // check if the code is the same
            if (newItem.getCOLUMN_PRODUCT_CODE() != getCOLUMN_PRODUCT_CODE()) {
                result = false;
            }
            // check if the quantity is the same
            if (newItem.getCOLUMN_PRODUCT_QUANTITY() != getCOLUMN_PRODUCT_QUANTITY()) {
                result = false;
            }
            // check if the quantity limit is the same
            if (newItem.getCOLUMN_PRODUCT_QUANTITY_LIMIT() != getCOLUMN_PRODUCT_QUANTITY_LIMIT()) {
                result = false;
            }

            return result;
        }
    }

    /**
     * Inner class that defines constant values for the bills database table.
     * Each entry in the table represents a single bill.
     */

    @Entity(tableName = "bills_table")
    public static final class BillsEntry {


        /**
         * the id of the bill
         * <p>
         * Type: INT
         */
        @PrimaryKey(autoGenerate = true)
        private int _BID;


        /**
         * the date of the bill
         * <p>
         * Type: INT
         */
        private long COLUMN_BILL_DATE;



        /**
         * the total price of the bill
         * <p>
         * Type: DOUBLE
         */
        private double COLUMN_BILL_TOTAL_PRICE;

        public BillsEntry(long COLUMN_BILL_DATE, double COLUMN_BILL_TOTAL_PRICE) {
            this.COLUMN_BILL_DATE = COLUMN_BILL_DATE;
            this.COLUMN_BILL_TOTAL_PRICE = COLUMN_BILL_TOTAL_PRICE;
        }

        public int get_BID() {
            return _BID;
        }

        public void set_BID(int _BID) {
            this._BID = _BID;
        }

        public long getCOLUMN_BILL_DATE() {
            return COLUMN_BILL_DATE;
        }

        public double getCOLUMN_BILL_TOTAL_PRICE() {
            return COLUMN_BILL_TOTAL_PRICE;
        }

    }

    /**
     * Inner class that defines constant values for the bill items database table.
     * Each entry in the table represents a single bill item.
     */
    @Entity(tableName = "bills_items_table")
    public static class BillsItemEntry implements BaseColumns {

        /**
         * the id of the bill
         * <p>
         * Type: INT
         */
        @PrimaryKey(autoGenerate = true)
        private int _ID;


        private int _BID;

        /**
         * the product_id of a product for a bill item
         * <p>
         * Type: INT
         */

        private String _PRODUCT_NAME;

        /**
         * the quantity of the product ( bill item )
         * <p>
         * Type: INT
         */
        private double COLUMN_BILL_ITEM_QUANTITY ;

        /**
         * the price of the bill item
         * <p>
         * Type: DOUBLE
         */
        private String COLUMN_BILL_ITEM_PRICE = "price";

        /**
         * the total price of the bill item = price * quantity
         * <p>
         * Type: DOUBLE
         */
        private double COLUMN_BILL_ITEM_TOTAL_PRICE ;


        public BillsItemEntry(int _BID, String _PRODUCT_NAME, double COLUMN_BILL_ITEM_QUANTITY,
                              String COLUMN_BILL_ITEM_PRICE, double COLUMN_BILL_ITEM_TOTAL_PRICE) {
            this._BID = _BID;
            this._PRODUCT_NAME = _PRODUCT_NAME;
            this.COLUMN_BILL_ITEM_QUANTITY = COLUMN_BILL_ITEM_QUANTITY;
            this.COLUMN_BILL_ITEM_PRICE = COLUMN_BILL_ITEM_PRICE;
            this.COLUMN_BILL_ITEM_TOTAL_PRICE = COLUMN_BILL_ITEM_TOTAL_PRICE;
        }


        public int get_BID() {
            return _BID;
        }

        public void set_ID(int _ID) {
            this._ID = _ID;
        }

        public int get_ID() {
            return _ID;
        }

        public String get_PRODUCT_NAME() {
            return _PRODUCT_NAME;
        }

        public double getCOLUMN_BILL_ITEM_QUANTITY() {
            return COLUMN_BILL_ITEM_QUANTITY;
        }

        public String getCOLUMN_BILL_ITEM_PRICE() {
            return COLUMN_BILL_ITEM_PRICE;
        }

        public double getCOLUMN_BILL_ITEM_TOTAL_PRICE() {
            return COLUMN_BILL_ITEM_TOTAL_PRICE;
        }
    }

    /**
     * Inner class that defines constant values for the logs database table.
     * Each entry in the table represents a single log.
     */
    @Entity(tableName = "log_table")
    public static class LogEntry implements BaseColumns {


        /**
         * the id of the bill
         * <p>
         * Type: INT
         */
        @PrimaryKey(autoGenerate = true)
        public int _LID;

        /**
         * the name of the log
         * <p>
         * Type: String
         */
        public String COLUMN_LOG_NAME = "name";

        /**
         * the type of the log
         * <p>
         * Type: INT
         */
        public int COLUMN_LOG_TYPE;

        /**
         * the value of the log
         * <p>
         * Type: DOUBLE
         */
        public double COLUMN_LOG_VALUE;


        public LogEntry(String COLUMN_LOG_NAME, int COLUMN_LOG_TYPE, double COLUMN_LOG_VALUE) {
            this.COLUMN_LOG_NAME = COLUMN_LOG_NAME;
            this.COLUMN_LOG_TYPE = COLUMN_LOG_TYPE;
            this.COLUMN_LOG_VALUE = COLUMN_LOG_VALUE;
        }

        public int get_LID() {
            return _LID;
        }

        public void set_LID(int _LID) {
            this._LID = _LID;
        }

        public String getCOLUMN_LOG_NAME() {
            return COLUMN_LOG_NAME;
        }

        public int getCOLUMN_LOG_TYPE() {
            return COLUMN_LOG_TYPE;
        }

        public double getCOLUMN_LOG_VALUE() {
            return COLUMN_LOG_VALUE;
        }

        /**
         * possible values for the type of the Log
         */

        public boolean isTypeValid(int type) {
            if (type == PROFIT || type == LOSS) {
                return true;
            }

            return false;
        }

    }


}
