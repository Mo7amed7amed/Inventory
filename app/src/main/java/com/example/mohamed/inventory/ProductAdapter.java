package com.example.mohamed.inventory;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mohamed on 2/7/2018.
 */

public class ProductAdapter extends CursorAdapter {
    public static ArrayList<Integer> id;
    public ProductAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view,final Context context,final Cursor cursor) {
        TextView tName = (TextView) view.findViewById(R.id.name);
        TextView tPrice = (TextView) view.findViewById(R.id.price);
        TextView tQauntity = (TextView) view.findViewById(R.id.quantity);
        String name = cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.COULMN_NAME));
        int price = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry.COULMN_PRICE));
        int quantity = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry.COULMN_QUANTITY));
        tName.setText(name);
        tPrice.setText("" + price);
        tQauntity.setText("" + quantity);
    }
}
