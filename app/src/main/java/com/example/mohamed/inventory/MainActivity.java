package com.example.mohamed.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
ProductAdapter adapter;
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cursor cursor = getContentResolver().query(ProductContract.ProductEntry.CONTENT_URI, null, null, null, null);
        adapter = new ProductAdapter(this, cursor);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        View view = findViewById(R.id.empty_text);
        listView.setEmptyView(view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {

                Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                intent.setData(Uri.withAppendedPath(ProductContract.ProductEntry.CONTENT_URI, String.valueOf(adapter.getItemId(position))));

                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void sale(View view) {
        View parentRow = (View) view.getParent();
        ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        long id = adapter.getItemId(position);
        Cursor c = getContentResolver().query(Uri.withAppendedPath(ProductContract.ProductEntry.CONTENT_URI, String.valueOf(id)), null, null, null, null);
        if (c.moveToFirst()) {
            String name = c.getString(c.getColumnIndex(ProductContract.ProductEntry.COULMN_NAME));
            int price = c.getInt(c.getColumnIndex(ProductContract.ProductEntry.COULMN_PRICE));
            int quantity = c.getInt(c.getColumnIndex(ProductContract.ProductEntry.COULMN_QUANTITY));
            String iUri = c.getString(c.getColumnIndex(ProductContract.ProductEntry.COULMN_IMAGE));

            if (quantity - 1 < 0) {
                Toast.makeText(this, "quantity can't less than 0", Toast.LENGTH_SHORT).show();
                return;
            } else {
                ContentValues values = new ContentValues();
                values.put(ProductContract.ProductEntry.COULMN_NAME, name);
                values.put(ProductContract.ProductEntry.COULMN_PRICE, price);
                values.put(ProductContract.ProductEntry.COULMN_QUANTITY, quantity - 1);
                values.put(ProductContract.ProductEntry.COULMN_IMAGE, iUri);
                getContentResolver().update(Uri.withAppendedPath(ProductContract.ProductEntry.CONTENT_URI, String.valueOf(id)), values, null, null);
            }
        }
    }
}
