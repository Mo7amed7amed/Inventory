package com.example.mohamed.inventory;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Mohamed on 2/7/2018.
 */

public class ProductAdapter extends CursorAdapter {

    public ProductAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tName = (TextView) view.findViewById(R.id.name);
        TextView tPrice = (TextView) view.findViewById(R.id.price);
        TextView tQauntity = (TextView) view.findViewById(R.id.quantity);
        ImageView ivImage = (ImageView) view.findViewById(R.id.image);

        String name = cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.COULMN_NAME));
        final String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COULMN_IMAGE));
        int price = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry.COULMN_PRICE));
        int quantity = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry.COULMN_QUANTITY));

        tName.setText(name);
        tPrice.setText("" + price);
        tQauntity.setText("" + quantity);
        if (imagePath != null) {
            ivImage.setVisibility(View.VISIBLE);
            ivImage.setImageURI(Uri.parse(imagePath));
        } else {
            ivImage.setVisibility(View.GONE);
        }
    }
}
