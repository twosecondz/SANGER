plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android) // Diambil dari rekan Anda (wajib untuk Kotlin)
    id("com.google.gms.google-services") // Diambil dari rekan Anda (wajib untuk Firebase)
}

android {
    // WAJIB Diskusi: Pilih satu namespace/ID permanen.
    // Sementara ini menggunakan versi rekan Anda karena terhubung ke Firebase.
    namespace = "com.example.sangerfinal"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.sangerfinal"
        minSdk = 24 // DI AMBIL DARI VERSI ANDA untuk jangkauan pengguna maksimal
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    // Diambil dari rekan Anda (wajib untuk Kotlin)
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Semua dependencies diambil dari versi rekan Anda karena lebih lengkap

    // KTX & Support Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation(libs.firebase.auth.ktx)
    implementation("com.google.firebase:firebase-firestore-ktx:24.0.0")
    implementation("com.google.firebase:firebase-storage:20.0.0")

    // Third-party UI Libraries
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit) // Menggunakan versi AndroidX yang lebih baru
    androidTestImplementation(libs.androidx.espresso.core)
}