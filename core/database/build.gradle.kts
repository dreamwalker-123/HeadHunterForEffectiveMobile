plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltAndroid)

    alias(libs.plugins.androidx.room)
}

android {
    namespace = "com.example.database"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

room {
    schemaDirectory("${rootProject.projectDir}/schemas")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Room
//    implementation(libs.androidx.room.ktx)
//    implementation(libs.androidx.room.runtime)
//    ksp(libs.androidx.room.compiler)

    implementation("com.google.code.gson:gson:2.11.0")
//    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

    // Hilt for android
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}