# Sanger - Aplikasi Pemesanan Biji Kopi ☕

Aplikasi mobile untuk mempermudah proses pemesanan biji kopi berkualitas langsung dari petani dan pelaku usaha di Indonesia.

## 📜 Tentang Proyek

**Sanger** adalah sebuah aplikasi mobile berbasis Android yang dikembangkan sebagai solusi modern atas tantangan yang sering dihadapi oleh para pelaku usaha dan pecinta kopi, yaitu kesulitan untuk mendapatkan akses terhadap biji kopi berkualitas secara praktis dan efisien. Proses pencarian dan pembelian yang seringkali masih bersifat manual memakan banyak waktu dan tenaga.

Untuk mengatasi masalah tersebut, **Sanger** hadir sebagai sebuah platform modern yang dirancang untuk mempermudah proses pemesanan biji kopi secara online. Melalui aplikasi ini, pengguna dapat dengan mudah mencari, memilih, dan memesan beragam jenis biji kopi terbaik langsung dari genggaman mereka, untuk kemudian diantarkan langsung ke lokasi tujuan.

Proyek ini dikembangkan untuk memenuhi tugas akhir mata kuliah **Pemrograman Berbasis Mobile**.

## ✨ Fitur Utama

Aplikasi Sanger dilengkapi dengan berbagai fitur untuk memberikan pengalaman pengguna yang lengkap:

* **Splash Screen & Alur Onboarding:** Pengalaman pertama yang mulus bagi pengguna baru dengan alur perkenalan aplikasi yang informatif.
* **Autentikasi Pengguna:** Sistem registrasi dan login yang aman menggunakan Firebase Authentication.
* **Manajemen Profil:**
    * Melihat data profil personal.
    * Mengedit informasi profil (nama, email, biodata).
    * Mengganti foto profil dengan mengunggah gambar dari galeri perangkat.
* **Alur Pemesanan:**
    * Keranjang Belanja (`CartActivity`).
    * Pilihan Metode Pembayaran (`PaymentMethodActivity`).
    * Konfirmasi Keberhasilan Pembayaran (`PaymentSuccessActivity`).
* **Integrasi Peta (Direncanakan):** Meminta akses lokasi untuk fitur pengantaran.

## 🛠️ Teknologi yang Digunakan

Proyek ini dibangun menggunakan teknologi dan library modern dalam ekosistem pengembangan Android.

* **Bahasa Pemrograman:** Kotlin & Java
* **Arsitektur:** Android SDK
* **IDE:** Android Studio
* **UI/UX:**
    * Material Design 3
    * XML Layouts dengan ConstraintLayout
* **Backend & Database:**
    * **Firebase Authentication** untuk manajemen pengguna.
    * **Cloud Firestore** sebagai database NoSQL untuk menyimpan data pengguna.
    * **Firebase Storage** untuk menyimpan file gambar (foto profil).
* **Library Pihak Ketiga:**
    * **Glide** untuk memuat dan menampilkan gambar secara efisien.
    * **CircleImageView** untuk menampilkan foto profil dalam bentuk lingkaran.
* **Manajemen Dependency:** Gradle dengan Version Catalogs (`libs.versions.toml`).

## ⚙️ Instalasi & Konfigurasi

Untuk menjalankan proyek ini di komputer lokal, ikuti langkah-langkah berikut:

1.  **Prasyarat:**
    * Android Studio (Direkomendasikan versi Iguana | 2023.2.1 atau lebih baru).
    * JDK 21.

2.  **Clone Repositori:**
    ```bash
    git clone [https://github.com/twosecondz/SANGER.git](https://github.com/twosecondz/SANGER.git)
    ```

3.  **Buka Proyek:**
    * Buka Android Studio.
    * Pilih `Open` dan arahkan ke folder `SANGER` yang baru saja Anda clone.

4.  **Konfigurasi Firebase (Langkah Krusial):**
    * Proyek ini memerlukan koneksi ke Firebase agar bisa berfungsi. Anda harus membuat proyek Firebase Anda sendiri.
    * Buka [Firebase Console](https://console.firebase.google.com/), buat proyek baru.
    * Daftarkan aplikasi Android Anda dengan nama paket (applicationId): `com.example.sangerfinal`.
    * Unduh file `google-services.json` yang disediakan.
    * Salin file `google-services.json` tersebut dan tempelkan ke dalam direktori `app/` pada proyek Anda di Android Studio.

5.  **Sinkronisasi Gradle:**
    * Setelah menambahkan `google-services.json`, Android Studio mungkin akan meminta Anda untuk melakukan **Gradle Sync**. Klik **"Sync Now"**.
    * Jika tidak muncul, klik ikon gajah dengan panah biru (Sync Project with Gradle Files) di toolbar atas.

6.  **Build dan Jalankan:**
    * Setelah proses sync selesai, klik `Build > Rebuild Project` untuk memastikan semua sudah benar.
    * Jalankan aplikasi dengan menekan tombol Play (segitiga hijau).

## 👥 Tim Pengembang

Proyek ini dikerjakan oleh:

* **M. Ihsan Rizqullah Adfa** - `2208107010029`
* **M. Syahidal Akbar Zas** - `2208107010045`

---
