package com.example.mohamed.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

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
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19){
         intent = new Intent(
                Intent.ACTION_GET_CONTENT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);}
                else{
            intent = new Intent( Intent.ACTION_GET_CONTENT,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        checkWriteToExternalPerms();
        intent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(intent, "Select an Image");
        startActivityForResult(chooserIntent, 1);
    }
    private void checkWriteToExternalPerms() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            } else {
            }
        } else {
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            imageUri = selectedImage;
            String[] projection = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, projection, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            cursor.close();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);

                ImageView imageView = (ImageView) findViewById(R.id.edit_image);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            ImageView imageView = (ImageView) findViewById(R.id.edit_image);
//
//            imageView.setImageURI(selectedImage);

        }
    }
}
