package com.example.sangerfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)

        // FirebaseAuth instance
        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnTogglePassword = findViewById<ImageButton>(R.id.btnTogglePassword)
        val btnLogin = findViewById<TextView>(R.id.btnLogin)
        val btnRegister = findViewById<TextView>(R.id.tvRegister) // Tombol Daftar

        // Toggle password visibility
        btnTogglePassword.setOnClickListener {
            if (etPassword.inputType == 129) { // TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD
                etPassword.inputType = 144 // TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                btnTogglePassword.setImageResource(R.drawable.ic_visibility_off)
            } else {
                etPassword.inputType = 129
                btnTogglePassword.setImageResource(R.drawable.ic_visibility)
            }
            etPassword.setSelection(etPassword.text.length)
        }

        // Tombol login
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }

        // Tombol daftar
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        // Email sudah diverifikasi
                        Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()

                        // Redirect ke MainActivity setelah login berhasil
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Tutup LoginActivity agar pengguna tidak bisa kembali
                    } else {
                        // Jika email belum diverifikasi
                        Toast.makeText(this, "Silakan verifikasi email Anda terlebih dahulu!", Toast.LENGTH_SHORT).show()
                        auth.signOut()  // Log out pengguna yang belum diverifikasi
                    }
                } else {
                    // Jika login gagal
                    Toast.makeText(this, "Login gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
