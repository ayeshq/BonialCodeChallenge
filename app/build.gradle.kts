import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

android {
    namespace = "com.bonial.challenge"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.bonial.challenge"
        minSdk = 24
        targetSdk = 36
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    testImplementation(libs.junit.junit)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Koin
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    testImplementation(libs.koin.test)

    //Compose
    implementation(platform(libs.androidx.compose))
    androidTestImplementation(platform(libs.androidx.compose))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Compose Material Design 3
    implementation(libs.androidx.compose.material3)

    // Android Studio Preview support
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Compose UI Tests
    androidTestImplementation(libs.androidx.compose.ui.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifist)

    // Compose Window size utils
    implementation(libs.androidx.compose.material3.adaptive.ui)

    implementation(libs.kotlinx.serialization.core)

    //Retrofit2
    implementation(platform(libs.retrofit2))
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit2.converter.gson)

    //Gson
    implementation(libs.gson)

    //Mockk
    testImplementation(libs.mockk)
}
