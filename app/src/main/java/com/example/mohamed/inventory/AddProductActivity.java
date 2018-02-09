package com.example.mohamed.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

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
            Toast.makeText(AddProductActivity.this, "You Must Enter All Fields", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            price = Integer.parseInt(editPrice.getText().toString().trim());
            quantity = Integer.parseInt(editQuantity.getText().toString().trim());
        } catch (Exception e) {
            Toast.makeText(AddProductActivity.this, "You Must Enter Numbers Only", Toast.LENGTH_SHORT).show();
            return;
        }
        if (imageUri == null) {
            Toast.makeText(AddProductActivity.this, "You Must Insert Image", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COULMN_NAME, name);
        values.put(ProductContract.ProductEntry.COULMN_PRICE, price);
        values.put(ProductContract.ProductEntry.COULMN_QUANTITY, quantity);
        values.put(ProductContract.ProductEntry.COULMN_IMAGE, imageUri.toString());
        getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);
        finish();

    }

    public void takePhoto(View view) {

        Intent intent = new Intent(
                Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(intent, "Select an Image");
        startActivityForResult(chooserIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri uriFromPath = data.getData();
//            String realPath;
//
//            // SDK < API11
//            if (Build.VERSION.SDK_INT < 11)
//                realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());
//
//                // SDK >= 11 && SDK < 19
//            else if (Build.VERSION.SDK_INT < 19)
//                realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());
//
//                // SDK > 19 (Android 4.4)
//
//             else   realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
//            Uri uriFromPath = Uri.fromFile(new File(realPath));

            imageUri = uriFromPath;

            ImageView imageView = (ImageView) findViewById(R.id.edit_image);

            imageView.setImageURI(uriFromPath);

        }
    }

}

