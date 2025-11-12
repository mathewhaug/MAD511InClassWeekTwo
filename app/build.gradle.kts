

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    //Kotlin annotation processor plugin for Room
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        //build.gradle :app
        //Get items from local.properties
        val amadeusClientId: String = providers.gradleProperty("AMADEUS_CLIENT_ID").orNull ?: ""
        val amadeusClientSecret: String = providers.gradleProperty("AMADEUS_CLIENT_SECRET").orNull ?: ""
        val amadeusBase: String = "https://test.api.amadeus.com"
        //expose to buildconfig
        buildConfigField("String", "AMADEUS_CLIENT_ID", "\"XUyRbcDgge14HI2lOpCuLVFxiOP2GrCD\"")
        buildConfigField("String", "AMADEUS_CLIENT_SECRET", "\"F45XC5jPa02ZCLSx\"")
        buildConfigField("String", "AMADEUS_BASE", "\"https://test.api.amadeus.com\"")

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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.preference)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.androidx.junit.ktx)
    kapt(libs.room.compiler)
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //Room annotation processor required for _Impl class
    kapt("androidx.room:room-compiler:2.6.0")

    //Kotlin coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.0")

    implementation("androidx.compose.runtime:runtime-livedata:1.6.0")
    implementation("androidx.compose.foundation:foundation:1.6.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    // Retrofit + Gson converter
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // OkHttp (client + logging)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")




    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(kotlin("test"))
    // For LiveData testing
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    //androidTestImplementation("androidx.fragment:fragment-testing:1.7.1")
    debugImplementation("androidx.fragment:fragment-testing:1.7.1")



}
