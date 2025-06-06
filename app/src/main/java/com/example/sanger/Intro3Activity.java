package com.example.sanger;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Intro3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro3);

        Button btnLanjut = findViewById(R.id.btnLanjut);
        TextView tvLewati = findViewById(R.id.tvLewati);

        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intro3Activity.this, Intro4Activity.class);
                startActivity(intent);
                finish();
            }
        });

        tvLewati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSignIn();
            }
        });
    }

    private void navigateToSignIn() {
        // Temporary placeholder
        android.widget.Toast.makeText(this, "Navigating to Sign In (to be implemented)", android.widget.Toast.LENGTH_SHORT).show();
    }
}