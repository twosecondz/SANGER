package com.example.sangerfinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class InfoPersonalActivity : AppCompatActivity() {

    private lateinit var userName: TextView
    private lateinit var usernameLengkap: TextView
    private lateinit var tvBiodata: TextView
    private lateinit var tvEmail: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_info_personal)

        // Initialize FirebaseAuth and Firestore instances
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Initialize views
        userName = findViewById(R.id.userName)
        usernameLengkap = findViewById(R.id.usernameLengkap)
        tvEmail = findViewById(R.id.tv_email)
        tvBiodata = findViewById(R.id.tv_bio)

        // Load user data from Firestore
        loadUserData()

        // Back button functionality
        val btnBack = findViewById<ImageButton>(R.id.btn_back)
        btnBack.setOnClickListener {
            // --- PERUBAHAN DI SINI ---
            // Mengganti onBackPressed() dengan finish() yang lebih modern.
            // Efeknya sama persis: menutup halaman ini dan kembali.
            finish()
        }

        // Edit Profile button click listener
        val btnUbah = findViewById<TextView>(R.id.btn_ubah)
        btnUbah.setOnClickListener {
            // Navigate to EditProfileActivity
            val intent = Intent(this, EditProfilActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadUserData()  // Reload user data every time the activity is resumed
    }
    private fun loadUserData() {
        // ... TIDAK ADA PERUBAHAN SAMA SEKALI PADA FUNGSI INI ...
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            // Retrieve user data from Firestore
            db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        // Retrieve the data (name and email)
                        val name = document.getString("nama")
                        val email = document.getString("email")
                        val biodata = document.getString("biodata")

                        // Log data for debugging
                        Log.d("FirestoreData", "Name: $name, Email: $email")

                        // Update UI with user data
                        if (name != null) {
                            userName.text = name // Set the short name on the userName TextView
                            usernameLengkap.text = name // Set the full name on usernameLengkap TextView
                        } else {
                            Toast.makeText(this, "Nama tidak ditemukan!", Toast.LENGTH_SHORT).show()
                        }

                        if (email != null) {
                            tvEmail.text = email // Set the email on tvEmail TextView
                        } else {
                            Toast.makeText(this, "Email tidak ditemukan!", Toast.LENGTH_SHORT).show()
                        }

                        if (biodata != null) {
                            tvBiodata.text = biodata // Set the biodata on tvBiodata TextView
                        }
                    } else {
                        Toast.makeText(this, "Data pengguna tidak ditemukan!", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Gagal mengambil data pengguna: $e", Toast.LENGTH_SHORT).show()
                }
        } else {
            // If there's no logged-in user, show a message or redirect to login page
            Toast.makeText(this, "Tidak ada pengguna yang login", Toast.LENGTH_SHORT).show()
        }
    }
}