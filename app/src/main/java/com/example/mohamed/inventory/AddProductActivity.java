package com.example.mohamed.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddProductActivity extends AppCompatActivity {
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }

    public void save(View view) {
        int price = 0;
        int quantity = 0;
        EditText editName = (EditText) findViewById(R.id.edit_name);
        EditText editPrice = (EditText) findViewById(R.id.edit_price);
        EditText editQuantity = (EditText) findViewById(R.id.edit_quantity);

        String name = editName.getText().toString().trim();
        if (name.isEmpty() || editPrice.getText().toString().trim().isEmpty() || editQuantity.getText().toString().trim().isEmpty()) {
            Toast.makeText(AddProductActivity.this, "you must enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            price = Integer.parseInt(editPrice.getText().toString().trim());
            quantity = Integer.parseInt(editQuantity.getText().toString().trim());
        } catch (Exception e) {
            Toast.makeText(AddProductActivity.this, "you must enter numbers only", Toast.LENGTH_SHORT).show();
            return;
        }
        if (imageUri == null) {
            Toast.makeText(AddProductActivity.this, "you must insert image", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COULMN_NAME, name);
        values.put(ProductContract.ProductEntry.COULMN_PRICE, price);
        values.put(ProductContract.ProductEntry.COULMN_QUANTITY, quantity);
        values.put(ProductContract.ProductEntry.COULMN_IMAGE, imageUri.toString());
        getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI,values);
        finish();

    }

    public void takePhoto(View view) {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            imageUri = selectedImage;

            ImageView imageView = (ImageView) findViewById(R.id.edit_image);

            imageView.setImageURI(selectedImage);
        }
    }
}
