plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.yg.drawer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.yg.drawer"
        minSdk = 29
        targetSdk = 35
        versionCode = 4
        versionName = "1.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // --- START: Changes to enable shrinking ---
            // Enables code shrinking, obfuscation, and optimization for the release build.
            // Changed from false to true.
            isMinifyEnabled = true

            // Enables resource shrinking, which removes unused resources.
            // Added this line.
            isShrinkResources = true
            // --- END: Changes to enable shrinking ---

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro" // Relative path to your custom rules file
            )
        }
        // You might have other build types here, like 'debug'
        // debug {
        //     isMinifyEnabled = false
        //     isShrinkResources = false
        //     // ...
        // }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.material) // You have material listed twice, you can remove one if not needed
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid) // Use the latest version
    implementation(libs.integrity)

    
}
