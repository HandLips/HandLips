// build.gradle.kts
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "id.handlips"
    compileSdk = 35

    defaultConfig {
        applicationId = "id.handlips"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "BASE_URL", "\"https://capstone-api-442707.et.r.appspot.com/\"")
        buildConfigField("String", "API_KEY", "\"781360183750-rfrqj37aemvv99m4im625ooscp3ui4vm.apps.googleusercontent.com\"")
        buildConfigField("String", "GEMINI_API", "\"https://gen-lang-client-0178483398.et.r.appspot.com\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = true
        compose = true
        mlModelBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.vertexai)
    implementation(libs.androidx.core.i18n)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.androidx.material3)

    // Android Studio Preview support
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

    // UI Tests
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Coil : image loading
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Media3
    implementation(libs.androidx.media3.exoplayer)
    // For building media playback UIs (opsional, jika ingin menggunakan built-in UI dari Media3)
    implementation(libs.androidx.media3.ui)
    // Common functionality for media components
    implementation(libs.androidx.media3.common)

    // Splash Screen
    implementation(libs.androidx.core.splashscreen)

    // sharePreference
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.accompanist.pager)
    implementation(libs.google.accompanist.pager.indicators)
    // CameraX
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.extensions)
    implementation(libs.gms.play.services.auth)

    // ML
    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)
//    implementation(libs.tensorflow.lite.gpu)
    implementation(libs.tensorflow.lite.task.vision)
}
