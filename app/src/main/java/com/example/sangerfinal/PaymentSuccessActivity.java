package com.example.sangerfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaymentSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        Button btnLacakPesanan = findViewById(R.id.btnLacakPesanan);

        btnLacakPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add navigation to order tracking
                // Example: Intent intent = new Intent(PaymentSuccessActivity.this, OrderTrackingActivity.class);
                // startActivity(intent);
                android.widget.Toast.makeText(PaymentSuccessActivity.this, "Navigate to Order Tracking - Add navigation logic", android.widget.Toast.LENGTH_SHORT).show();
            }
        });
    }
}