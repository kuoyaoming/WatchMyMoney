import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.watchmymoney"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.watchmymoney"
        minSdk = 30 // Wear OS 3.0+
        targetSdk = 34 // Android 14 (Wear OS 5 target)
        versionCode = 2
        versionName = "1.1"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            val keystoreFile = file("keystore.properties")
            if (keystoreFile.exists()) {
                val props = Properties()
                props.load(FileInputStream(keystoreFile))
                storeFile = file(props.getProperty("storeFile"))
                storePassword = props.getProperty("storePassword")
                keyAlias = props.getProperty("keyAlias")
                keyPassword = props.getProperty("keyPassword")
            } else if (System.getenv("ANDROID_KEYSTORE_FILE") != null) {
                storeFile = file(System.getenv("ANDROID_KEYSTORE_FILE"))
                storePassword = System.getenv("ANDROID_KEYSTORE_PASSWORD")
                keyAlias = System.getenv("ANDROID_KEY_ALIAS")
                keyPassword = System.getenv("ANDROID_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
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
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.play.services.wearable)
    implementation(libs.androidx.wear)
    
    // Compose
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.wear.compose.material)
    implementation(libs.androidx.wear.compose.foundation)
    
    // Complications
    implementation(libs.androidx.watchface.complications.data.source.ktx)
    
    // DataStore
    implementation(libs.androidx.datastore.preferences)
    
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.wear.input)
    implementation(libs.androidx.compose.material.icons.extended)
}
