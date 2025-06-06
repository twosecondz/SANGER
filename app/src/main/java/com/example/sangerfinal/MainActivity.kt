package com.example.sangerfinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private lateinit var menuIcon: ImageView
    private lateinit var profilePicture: ImageView
    private lateinit var userName: TextView
    private lateinit var greetingText: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        // Initialize FirebaseAuth and Firestore instances
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Initialize views
        initViews()

        // Setup click listeners
        setupClickListeners()

        // Load user data
        loadUserData()
    }

    override fun onResume() {
        super.onResume()
        loadUserData()  // Reload user data every time the activity is resumed
    }

    private fun initViews() {
        menuIcon = findViewById(R.id.menuIcon)
        profilePicture = findViewById(R.id.img_profile)
        userName = findViewById(R.id.userName)
        greetingText = findViewById(R.id.greetingText)
    }

    private fun setupClickListeners() {
        menuIcon.setOnClickListener {
            // Handle menu click
            openMenu()
        }

        profilePicture.setOnClickListener {
            // Handle profile picture click
            openProfile()
        }
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
                        // Get the user data from Firestore
                        val name = document.getString("nama")
                        val email = document.getString("email")
                        val pictureUrl = document.getString("profileImageUrl")

                        // Log data for debugging
                        Log.d("FirestoreData", "Name: $name, Email: $email, Picture URL: $pictureUrl")

                        // Update UI with user data
                        if (name != null) {
                            userName.text = name // Set the user name
                            greetingText.text = "Hey $name, Silahkan Pesan!" // Display greeting message
                        } else {
                            Toast.makeText(this, "Nama tidak ditemukan!", Toast.LENGTH_SHORT).show()
                        }

                        // Load profile picture using Glide
                        if (!pictureUrl.isNullOrEmpty()) {
                            Glide.with(this)
                                .load(pictureUrl) // Load the image from the URL
                                .placeholder(R.drawable.default_avatar) // Placeholder image while loading
                                .error(R.drawable.default_avatar) // Error image in case of failure
                                .into(profilePicture) // Set image into ImageView
                        } else {
                            // Handle the case when the pictureUrl is null
                            profilePicture.setImageResource(R.drawable.default_avatar) // Set a default image
                        }
                    } else {
                        Toast.makeText(this, "Data pengguna tidak ditemukan!", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Gagal mengambil data pengguna: $e", Toast.LENGTH_SHORT).show()
                }
        } else {
            // If no user is logged in
            Toast.makeText(this, "Tidak ada pengguna yang login", Toast.LENGTH_SHORT).show()
        }
    }


    private fun openMenu() {
        // TODO: Implement menu functionality
        // This could open a navigation drawer or show a popup menu
        menuIcon.setOnClickListener {
            val intent = Intent(this, ProfilActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openProfile() {
        // TODO: Implement profile functionality
        // Tombol daftar
        profilePicture.setOnClickListener {
            val intent = Intent(this, ProfilActivity::class.java)
            startActivity(intent)
        }
    }
}
