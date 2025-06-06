package com.example.sanger;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Set up the styled text for the SANGER logo
        TextView logoText = findViewById(R.id.tvSangerLogo);
        logoText.setText(Html.fromHtml(getString(R.string.app_name_styled), Html.FROM_HTML_MODE_LEGACY));

        // Set click listener for the entire screen
        findViewById(R.id.splashContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToIntro1();
            }
        });

        // Auto navigate after 5 seconds if user doesn't click
        new Handler().postDelayed(() -> {
            navigateToIntro1();
        }, 5000);
    }

    private void navigateToIntro1() {
        Intent intent = new Intent(SplashActivity.this, Intro1Activity.class);
        startActivity(intent);
        finish();
    }
}