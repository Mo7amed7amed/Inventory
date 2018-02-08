package com.example.mohamed.inventory;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;

public class ProductDetailActivity extends AppCompatActivity {
    private Uri uri;
    private String name;
    private int price;
    private int quantity;
    private String iUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent intent = getIntent();
        uri = intent.getData();
        ActivityCompat.requestPermissions(ProductDetailActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    show(uri);
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    private void show(Uri uri) {
        Cursor c = getContentResolver().query(uri, null, null, null, null);
        TextView tname = (TextView) findViewById(R.id.dname);
        TextView tprice = (TextView) findViewById(R.id.dprice);
        TextView tquantity = (TextView) findViewById(R.id.dquantity);
        ImageView imageView = (ImageView) findViewById(R.id.dimage);

        if (c.moveToFirst()) {
            name = c.getString(c.getColumnIndex(ProductContract.ProductEntry.COULMN_NAME));
            price = c.getInt(c.getColumnIndex(ProductContract.ProductEntry.COULMN_PRICE));
            tname.setText(name);
            tprice.setText(String.valueOf(price));
            quantity = c.getInt(c.getColumnIndex(ProductContract.ProductEntry.COULMN_QUANTITY));
            tquantity.setText(String.valueOf(quantity));
            iUri = c.getString(c.getColumnIndex(ProductContract.ProductEntry.COULMN_IMAGE));
            imageView.setImageURI(Uri.parse(iUri));
        }
    }
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.details_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Delete this product");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getContentResolver().delete(uri, null, null);
                        finish();

                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    public void increase(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("increase quantity");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                try {
                    final int extra = Integer.parseInt(m_Text);
                    quantity += extra;
                    ContentValues values = new ContentValues();
                    values.put(ProductContract.ProductEntry.COULMN_NAME, name);
                    values.put(ProductContract.ProductEntry.COULMN_PRICE, price);
                    values.put(ProductContract.ProductEntry.COULMN_QUANTITY, quantity);
                    values.put(ProductContract.ProductEntry.COULMN_IMAGE, iUri);

                    getContentResolver().update(uri, values, null, null);
                    show(uri);


                } catch (Exception e) {
                    Toast.makeText(ProductDetailActivity.this, "you must enter numbers only", Toast.LENGTH_SHORT);

                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void decrease(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("decrease quantity");
        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                try {
                    final int decrease = Integer.parseInt(m_Text);
                    int newValue = quantity - decrease;
                    if (newValue < 0) {
                        Toast.makeText(ProductDetailActivity.this, "QUANTITY MUSTN'T LESS THAN 0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        quantity=newValue;
                    }
                    ContentValues values = new ContentValues();
                    values.put(ProductContract.ProductEntry.COULMN_NAME, name);
                    values.put(ProductContract.ProductEntry.COULMN_PRICE, price);
                    values.put(ProductContract.ProductEntry.COULMN_QUANTITY, quantity);
                    values.put(ProductContract.ProductEntry.COULMN_IMAGE, iUri);

                    getContentResolver().update(uri, values, null, null);
                    show(uri);


                } catch (Exception e) {
                    Toast.makeText(ProductDetailActivity.this, "you must enter numbers only", Toast.LENGTH_SHORT);

                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();


    }
    public void order(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        String message = "Kindlly we want some " + name + " to our inventory";
        intent.putExtra(Intent.EXTRA_SUBJECT, "HELLO");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
