package com.example.sangerfinal

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_register)

        // FirebaseAuth instance
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val btnBack = findViewById<CardView>(R.id.btnBack)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etNama = findViewById<EditText>(R.id.etNama)  // Username field
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)

        val btnTogglePassword = findViewById<ImageButton>(R.id.btnTogglePassword)

        // Aksi tombol kembali
        btnBack.setOnClickListener {
            // Menggunakan OnBackPressedDispatcher untuk menangani tombol kembali
            onBackPressedDispatcher.onBackPressed()
        }


        // Aksi tombol sign-up
        btnSignUp.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val nama = etNama.text.toString().trim()  // Ambil nama pengguna

            if (email.isEmpty() || password.isEmpty() || nama.isEmpty()) {
                Toast.makeText(this, "Email, password, dan nama harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(email, password, nama)
            }
        }

        // Aksi untuk menampilkan atau menyembunyikan password
        btnTogglePassword.setOnClickListener {
            if (etPassword.inputType == 129) {  // TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD
                etPassword.inputType = 144 // TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                btnTogglePassword.setImageResource(R.drawable.ic_visibility_off)
            } else {
                etPassword.inputType = 129
                btnTogglePassword.setImageResource(R.drawable.ic_visibility)
            }
            etPassword.setSelection(etPassword.text.length)
        }
    }

    private fun registerUser(email: String, password: String, nama: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Pendaftaran berhasil
                    val user = auth.currentUser

                    // Simpan nama pengguna ke Firestore
                    val userId = user?.uid
                    val userData = hashMapOf(
                        "nama" to nama,
                        "email" to email,
                        "biodata" to "Belum ada biodata",
                        "profileImageUrl" to "default_profile_image_url" // Default profile image URL
                    )

                    if (userId != null) {
                        db.collection("users").document(userId)  // Simpan data pengguna dengan ID pengguna Firebase
                            .set(userData)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Data pengguna berhasil disimpan!", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Gagal menyimpan data pengguna: $e", Toast.LENGTH_SHORT).show()
                            }
                    }

//                    Kirim email verifikasi setelah pendaftaran
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { verificationTask ->
                            if (verificationTask.isSuccessful) {
                                Toast.makeText(this, "Email verifikasi telah dikirim!", Toast.LENGTH_SHORT).show()
                            }
                        }

                    // Redirect ke halaman login setelah pendaftaran berhasil
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish() // Tutup activity register

                } else {
                    // Jika email sudah terdaftar, tampilkan pesan kesalahan
                    val errorMessage = task.exception?.message
                    if (errorMessage != null && errorMessage.contains("The email address is already in use")) {
                        // Menampilkan Toast jika email sudah terdaftar
                        Toast.makeText(this, "Akun dengan email ini sudah terdaftar!", Toast.LENGTH_SHORT).show()
                    } else {
                        // Tampilkan pesan kesalahan umum
                        Toast.makeText(this, "Pendaftaran gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
