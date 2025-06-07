package com.example.sangerfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // Set up back button
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish() // This will close the current activity and return to ProfilActivity
        }

        // Set up Masukan Pesanan button
        val btnMasukanPesanan = findViewById<Button>(R.id.btnMasukanPesanan)
        btnMasukanPesanan.setOnClickListener {
            val intent = Intent(this, PaymentMethodActivity::class.java)
            startActivity(intent)
        }
    }
} 