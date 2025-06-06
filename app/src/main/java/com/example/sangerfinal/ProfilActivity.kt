package com.example.sangerfinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfilActivity : AppCompatActivity() {

    private lateinit var userName: TextView
    private lateinit var tvBiodata: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_profil)

        // Initialize FirebaseAuth and Firestore instances
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Initialize views
        userName = findViewById(R.id.userName)
        tvBiodata = findViewById(R.id.tv_bio)

        // Fetch user data and set to userName
        loadUserData()

        val btnBack = findViewById<ImageButton>(R.id.btn_back)

        btnBack.setOnClickListener {
            // This will navigate back to the previous activity in the stack
            onBackPressed()
        }

        // Info Personal section click listener
        val layoutInfoPersonal = findViewById<LinearLayout>(R.id.layout_info_personal)
        layoutInfoPersonal.setOnClickListener {
            // Navigate to InfoPersonalActivity
            val intent = Intent(this, InfoPersonalActivity::class.java)
            startActivity(intent)
        }

        // Log Out section click listener
        val layoutKeluar = findViewById<LinearLayout>(R.id.layout_keluar)
        layoutKeluar.setOnClickListener {
            // Navigate to LoginActivity (you can also clear the session here if needed)
            val intent = Intent(this, LoginActivity::class.java)
            // If you want to clear the back stack (user can't go back after logging out)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish() // Optionally finish the current activity to remove it from the stack
        }
    }

    override fun onResume() {
        super.onResume()
        loadUserData()  // Reload user data every time the activity is resumed
    }

    private fun loadUserData() {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            // Retrieve user data from Firestore
            db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val name = document.getString("nama")
                        val biodata = document.getString("biodata")
                        // Log data for debugging
                        Log.d("FirestoreData", "Name: $name")

                        // Update UI with user data
                        if (name != null) {
                            userName.text = name // Set the user name on the TextView
                        } else {
                            Toast.makeText(this, "Nama tidak ditemukan!", Toast.LENGTH_SHORT).show()
                        }

                        if(biodata != null){
                            tvBiodata.text = biodata
                        }else{
                            Toast.makeText(this, "Biodata tidak ditemukan!", Toast.LENGTH_SHORT).show()
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
