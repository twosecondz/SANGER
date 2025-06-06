package com.example.sanger;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LocationAccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_access);

        Button btnAksesLokasi = findViewById(R.id.btnAksesLokasi);

        btnAksesLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add navigation logic here
                // Example: Intent intent = new Intent(LocationAccessActivity.this, NextActivity.class);
                // startActivity(intent);
                android.widget.Toast.makeText(LocationAccessActivity.this, "Location access requested - Add navigation logic", android.widget.Toast.LENGTH_SHORT).show();
            }
        });
    }
}