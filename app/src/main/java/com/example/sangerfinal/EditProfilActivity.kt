package com.example.sangerfinal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class EditProfilActivity : AppCompatActivity() {

    private lateinit var etNamaLengkap: EditText
    private lateinit var etEmail: EditText
    private lateinit var etBiodata: EditText
    private lateinit var btnSimpan: Button
    private lateinit var btnBack: ImageButton
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var btnChangeImage: ImageButton
    private lateinit var storage: FirebaseStorage
    private lateinit var profilePicture : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_edit_profil)

        // Initialize FirebaseAuth and Firestore instances
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Initialize views
        etNamaLengkap = findViewById(R.id.et_nama_lengkap)
        etEmail = findViewById(R.id.et_email)
        etBiodata = findViewById(R.id.et_biodata)
        btnSimpan = findViewById(R.id.btn_simpan)
        btnBack = findViewById(R.id.btn_back)
        btnChangeImage = findViewById(R.id.btn_edit_photo)
        profilePicture = findViewById(R.id.img_profile)


        // Load current user data from Firestore
        loadUserData()

        // Set onClickListener for the back button
        btnBack.setOnClickListener {
            onBackPressed()
        }

        // Set onClickListener for the "SIMPAN" button
        btnSimpan.setOnClickListener {
            // Save updated data to Firestore
            saveUserData()
        }

        btnChangeImage.setOnClickListener {
            // Allow user to pick a new image for profile picture
            pickImage()
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"  // Filter for image files
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private val IMAGE_PICK_CODE = 1000

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val imageUri = data?.data
            // Get the URI of the selected image
            Log.d("Image URI", imageUri.toString())
            uploadProfileImage(imageUri)  // Upload the image to Firebase Storage
        }
    }

    private fun uploadProfileImage(imageUri: Uri?) {
        if (imageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference.child("profile_images/${auth.currentUser?.uid}")
            val uploadTask = storageRef.putFile(imageUri)  // Upload the selected image to Firebase Storage

            uploadTask.addOnSuccessListener {
                // Get the download URL of the uploaded image
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    val profileImageUrl = uri.toString()

                    // Update Firestore with the new profile image URL
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        db.collection("users").document(userId)
                            .update("profileImageUrl", profileImageUrl)  // Update Firestore with new image URL
                            .addOnSuccessListener {
                                Toast.makeText(this, "Profile image updated!", Toast.LENGTH_SHORT).show()

                                // Update the profile picture in the UI immediately after uploading
                                Glide.with(this)
                                    .load(profileImageUrl)
                                    .placeholder(R.drawable.default_avatar)  // Default image while loading
                                    .error(R.drawable.default_avatar)  // Error image
                                    .into(profilePicture)  // Set the ImageView with the new profile image
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error updating profile image: $e", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error uploading image: $e", Toast.LENGTH_SHORT).show()
                }
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
                        val pictureUrl = document.getString("profileImageUrl")
                        val name = document.getString("nama")
                        val email = document.getString("email")
                        val biodata = document.getString("biodata")

                        // Load profile picture using Glide if URL exists
                        if (!pictureUrl.isNullOrEmpty()) {
                            Glide.with(this)
                                .load(pictureUrl)
                                .placeholder(R.drawable.default_avatar)
                                .error(R.drawable.default_avatar)
                                .into(profilePicture)
                        }

                        if(name != null){
                            etNamaLengkap.setText(name)
                        }else{
                            Toast.makeText(this, "Nama tidak ditemukan!", Toast.LENGTH_SHORT).show()
                        }

                        if( email != null){
                            etEmail.setText(email)
                        }else{
                            Toast.makeText(this, "Email tidak ditemukan!", Toast.LENGTH_SHORT).show()
                        }

                        if(biodata!= null) {
                            etBiodata.setText(biodata)
                        }
                    } else {
                        Toast.makeText(this, "User data not found!", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error loading user data: $e", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun saveUserData() {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            // Get the updated values from EditText fields
            val updatedName = etNamaLengkap.text.toString()
            val updatedEmail = etEmail.text.toString()
            val updatedBiodata = etBiodata.text.toString()

            // Check if the fields are not empty
            if (updatedName.isNotEmpty() && updatedEmail.isNotEmpty() && updatedBiodata.isNotEmpty()) {
                // Create a map with the updated values
                val userUpdates = hashMapOf(
                    "nama" to updatedName,
                    "email" to updatedEmail,
                    "biodata" to updatedBiodata
                )

                // Update the data in Firestore
                db.collection("users")
                    .document(userId)
                    .update(userUpdates as Map<String, Any>)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
                        // Optionally, navigate back or show a success message
                        finish() // Close the activity after saving
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Gagal menyimpan data: $e", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
