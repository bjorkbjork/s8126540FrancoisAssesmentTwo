plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")

}

android {
    namespace = "com.example.s8126540francoisassessmenttwo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.s8126540francoisassessmenttwo"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    packaging {
        resources {
            // Exclude duplicate META-INF files
            excludes += "META-INF/*"
        }
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.logging.interceptor)

    implementation(libs.hilt.android)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter)
    kapt(libs.dagger.hilt.android.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    testImplementation(libs.mockk) // Core MockK library for local unit tests
    testImplementation(libs.mockk.android) // Android-specific MockK for local unit tests
    testImplementation(libs.mockk.agent) // MockK agent for advanced mocking (e.g., static methods)
    testImplementation(libs.junit) // JUnit for local unit tests
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.androidx.core.testing)

    // Instrumented test dependencies (run on an Android device or emulator)
    androidTestImplementation (libs.mockk.android)// Android- specific MockK for instrumented tests
    androidTestImplementation(libs.mockk.agent) // MockK agent for advanced mocking in instrumented tests
    //androidTestImplementation(libs.androidx.junit) // AndroidX JUnit for instrumented tests
    //androidTestImplementation(libs.jetbrains.kotlinx.coroutines.test)
    //androidTestImplementation(libs.androidx.core.testing)



}



kapt {
    correctErrorTypes = true
}
