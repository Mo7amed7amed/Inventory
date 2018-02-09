package com.example.mohamed.inventory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mohamed.inventory.ProductContract.ProductEntry;

/**
 * Created by Mohamed on 2/8/2018.
 */

public class ProductDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "products";
    private static final int VERSION = 3;

    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + ProductEntry.TABLE_NAME + " ("
                + ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProductEntry.COULMN_NAME + " TEXT NOT NULL, "
                + ProductEntry.COULMN_PRICE + " FLOAT NOT NULL DEFAULT 0.00, "
                + ProductEntry.COULMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + ProductEntry.COULMN_IMAGE + " TEXT);";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
