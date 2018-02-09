package com.example.mohamed.inventory;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mohamed on 2/8/2018.
 */

public class ProductContract {
    public ProductContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.mohamed.inventory";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PRODUCTS = "products";

    public static final class ProductEntry implements BaseColumns {
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);

        public static final String TABLE_NAME = "wares";
        public static final String COULMN_NAME = "name";
        public static final String COULMN_QUANTITY = "quantity";
        public static final String COULMN_PRICE = "price";
        public static final String COULMN_IMAGE = "image";
    }
}
