package com.example.webandcraftsakhil;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.webandcraftsakhil.requests.category.Products;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";
    Products products;
    TextView tvTitle, tvPrice, tvDescription;
    ImageView ivDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvTitle = findViewById(R.id.tv_title_big);
        tvPrice = findViewById(R.id.tv_price_big);
        tvDescription = findViewById(R.id.tv_description);
        ivDescription = findViewById(R.id.iv_large);
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("product")) {
            products = (Products) getIntent().getSerializableExtra("product");
            Log.d(TAG, "getIncomingIntent: " + products.getDescription());
            setProductInfo(products);
        }
    }

    private void setProductInfo(Products products) {
        Log.d(TAG, "setProductInfo: " + products.getTitle());
        tvTitle.setText(products.getTitle());
        tvPrice.setText("₹" + String.valueOf(products.getPrice()));
        tvDescription.setText(products.getDescription());
        Glide.with(this)
                .asBitmap()
                .placeholder(R.drawable.ic_launcher_background)
                .load(products.imageUrl)
                .into(ivDescription);
    }
}