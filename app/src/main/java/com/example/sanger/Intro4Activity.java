package com.example.sanger;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Intro4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro4);

        Button btnMulai = findViewById(R.id.btnMulai);

        btnMulai.setOnClickListener(new View.OnClickListener() {
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