package com.example.sanger;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ImageButton btnBack = findViewById(R.id.btnBack);
        Button btnMasukanPesanan = findViewById(R.id.btnMasukanPesanan);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add back navigation logic here
                finish();
            }
        });

        btnMasukanPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add navigation to payment method
                // Example: Intent intent = new Intent(CartActivity.this, PaymentMethodActivity.class);
                // startActivity(intent);
                android.widget.Toast.makeText(CartActivity.this, "Navigate to Payment Method - Add navigation logic", android.widget.Toast.LENGTH_SHORT).show();
            }
        });
    }
}