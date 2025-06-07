package com.example.sangerfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences; // Import yang dibutuhkan
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
                // Panggil satu metode yang melakukan semua pekerjaan
                finishIntroAndNavigateToLogin();
            }
        });
    }

    /**
     * Metode ini melakukan 3 hal penting:
     * 1. Menyimpan data ke SharedPreferences bahwa intro sudah dilihat.
     * 2. Menyiapkan dan memulai intent untuk pindah ke LoginActivity.
     * 3. Menutup semua activity intro agar pengguna tidak bisa kembali.
     */
    private void finishIntroAndNavigateToLogin() {
        // Langkah 1: Tandai bahwa pengguna sudah melihat intro di "buku catatan"
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("HAS_SEEN_INTRO", true);
        editor.apply(); // Simpan perubahan

        // Langkah 2: Arahkan pengguna ke halaman Login
        Intent intent = new Intent(Intro4Activity.this, LoginActivity.class);
        startActivity(intent);

        // Langkah 3: Tutup semua activity intro (Intro1-4) agar tidak bisa kembali
        finishAffinity();
    }
}