package com.example.sangerfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class PaymentMethodActivity extends AppCompatActivity {

    private LinearLayout paymentCash, paymentVisa, paymentBSI;
    private String selectedPaymentMethod = "BSI"; // Default selection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        ImageButton btnBack = findViewById(R.id.btnBack);
        Button btnTambah = findViewById(R.id.btnTambah);
        Button btnBayarKonfirmasi = findViewById(R.id.btnBayarKonfirmasi);

        paymentCash = findViewById(R.id.paymentCash);
        paymentVisa = findViewById(R.id.paymentVisa);
        paymentBSI = findViewById(R.id.paymentBSI);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Payment method selection listeners
        paymentCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPaymentMethod("Cash");
            }
        });

        paymentVisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPaymentMethod("Visa");
            }
        });

        paymentBSI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPaymentMethod("BSI");
            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add card addition logic
                android.widget.Toast.makeText(PaymentMethodActivity.this, "Add card functionality - Add logic", android.widget.Toast.LENGTH_SHORT).show();
            }
        });

        btnBayarKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add navigation to payment success
                // Example: Intent intent = new Intent(PaymentMethodActivity.this, PaymentSuccessActivity.class);
                // startActivity(intent);
                android.widget.Toast.makeText(PaymentMethodActivity.this, "Navigate to Payment Success - Add navigation logic", android.widget.Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectPaymentMethod(String method) {
        selectedPaymentMethod = method;
        // TODO: Update UI to show selected payment method
        android.widget.Toast.makeText(this, method + " selected", android.widget.Toast.LENGTH_SHORT).show();
    }
}