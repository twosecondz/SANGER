package com.example.sangerfinal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)

        // Mengatur teks logo yang berwarna (dari kode Anda)
        val logoText = findViewById<TextView>(R.id.tvSangerLogo)
        logoText.text = Html.fromHtml(getString(R.string.app_name_styled), Html.FROM_HTML_MODE_LEGACY)

        // Handler untuk menunda navigasi
        Handler(Looper.getMainLooper()).postDelayed({

            // ===== LOGIKA KEPUTUSAN DIMULAI DI SINI =====

            // 1. Buka "buku catatan" bernama "AppPrefs"
            val sharedPrefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

            // 2. Cek apakah ada catatan "HAS_SEEN_INTRO". Jika tidak ada, anggap false.
            val hasSeenIntro = sharedPrefs.getBoolean("HAS_SEEN_INTRO", false)

            // 3. Tentukan tujuan berdasarkan catatan
            val destinationActivity = if (hasSeenIntro) {
                // Jika sudah pernah lihat intro, tujuan ke LoginActivity
                LoginActivity::class.java
            } else {
                // Jika belum pernah (pengguna baru), tujuan ke Intro1Activity
                Intro1Activity::class.java
            }

            // 4. Buat Intent ke tujuan yang sudah ditentukan
            val intent = Intent(this, destinationActivity)
            startActivity(intent)
            finish() // Hapus SplashActivity dari back stack

            // =================================================

        }, 2000)  // Durasi splash 2 detik sudah cukup
    }
}