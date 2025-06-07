package com.example.sangerfinal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Intro1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro1);

        Button btnLanjut = findViewById(R.id.btnLanjut);
        TextView tvLewati = findViewById(R.id.tvLewati);

        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intro1Activity.this, Intro2Activity.class);
                startActivity(intent);
                finish();
            }
        });

        tvLewati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Sign In Activity (will be created later)
                navigateToSignIn();
            }
        });
    }

    private void navigateToSignIn() {
        // For now, we'll just show a toast since SignIn activity doesn't exist yet
        // Intent intent = new Intent(Intro1Activity.this, SignInActivity.class);
        // startActivity(intent);
        // finish();

        // Temporary placeholder
        android.widget.Toast.makeText(this, "Navigating to Sign In (to be implemented)", android.widget.Toast.LENGTH_SHORT).show();
    }
}