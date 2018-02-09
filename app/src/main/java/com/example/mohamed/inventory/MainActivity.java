package com.example.mohamed.inventory;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mohamed.inventory.ProductContract.ProductEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int PRODUCT_LOADER = 0;
    ProductAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView productListView = (ListView) findViewById(R.id.list_view_product);

        View emptyView = findViewById(R.id.empty_view);
        productListView.setEmptyView(emptyView);
        mCursorAdapter = new ProductAdapter(this, null);
        productListView.setAdapter(mCursorAdapter);
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                Uri currentPetUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);
                intent.setData(currentPetUri);
                startActivity(intent);
            }
        });
        getLoaderManager().initLoader(PRODUCT_LOADER, null, this);
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
        long id = mCursorAdapter.getItemId(position);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COULMN_NAME,
                ProductEntry.COULMN_PRICE,
                ProductEntry.COULMN_QUANTITY,
                ProductEntry.COULMN_IMAGE};

        return new CursorLoader(this,
                ProductEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
