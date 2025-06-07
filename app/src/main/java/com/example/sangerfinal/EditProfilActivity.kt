package com.example.sangerfinal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
    private lateinit var profilePicture: ImageView

    // --- PERUBAHAN 1: Deklarasi "Peluncur" untuk Activity Result API ---
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

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

        // --- PERUBAHAN 2: Mendaftarkan Callback untuk Activity Result API ---
        // Kode ini menggantikan fungsi onActivityResult yang lama.
        // Ini mendefinisikan apa yang harus dilakukan SETELAH gambar dipilih.
        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageUri = result.data?.data
                if (imageUri != null) {
                    Log.d("Image URI", imageUri.toString())
                    // Memanggil fungsi upload yang sama seperti sebelumnya, tidak ada yang diubah di sini.
                    uploadProfileImage(imageUri)
                }
            }
        }

        // Load current user data from Firestore
        loadUserData()

        // --- PERUBAHAN 3: Memperbaiki Tombol Kembali ---
        // Menggunakan finish() adalah cara modern dan langsung untuk menutup activity.
        btnBack.setOnClickListener {
            finish()
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
        intent.type = "image/*"
        // --- PERUBAHAN 4: Menggunakan "Peluncur" yang baru ---
        imagePickerLauncher.launch(intent)
    }

    // --- Kode lama yang DIHAPUS ---
    // private val IMAGE_PICK_CODE = 1000
    // override fun onActivityResult(...) { ... }

    private fun uploadProfileImage(imageUri: Uri?) {
        // ... TIDAK ADA PERUBAHAN SAMA SEKALI PADA FUNGSI INI ...
        if (imageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference.child("profile_images/${auth.currentUser?.uid}")
            val uploadTask = storageRef.putFile(imageUri)

            uploadTask.addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    val profileImageUrl = uri.toString()
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        db.collection("users").document(userId)
                            .update("profileImageUrl", profileImageUrl)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Profile image updated!", Toast.LENGTH_SHORT).show()
                                Glide.with(this)
                                    .load(profileImageUrl)
                                    .placeholder(R.drawable.default_avatar)
                                    .error(R.drawable.default_avatar)
                                    .into(profilePicture)
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
        // ... TIDAK ADA PERUBAHAN SAMA SEKALI PADA FUNGSI INI ...
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val pictureUrl = document.getString("profileImageUrl")
                        val name = document.getString("nama")
                        val email = document.getString("email")
                        val biodata = document.getString("biodata")
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
        // ... TIDAK ADA PERUBAHAN SAMA SEKALI PADA FUNGSI INI ...
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val updatedName = etNamaLengkap.text.toString()
            val updatedEmail = etEmail.text.toString()
            val updatedBiodata = etBiodata.text.toString()
            if (updatedName.isNotEmpty() && updatedEmail.isNotEmpty() && updatedBiodata.isNotEmpty()) {
                val userUpdates = hashMapOf(
                    "nama" to updatedName,
                    "email" to updatedEmail,
                    "biodata" to updatedBiodata
                )
                db.collection("users")
                    .document(userId)
                    .update(userUpdates as Map<String, Any>)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
                        finish()
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