plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.nitrosoft.ua.poweradapter"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            @Suppress("UnstableApiUsage")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
}

val androidXCoreVersion = "1.10.1"
val androidXCore = "androidx.core:core:$androidXCoreVersion"

val cardViewVersion = "1.0.0"
val cardView = "androidx.cardview:cardview:$cardViewVersion"

val recyclerviewVersion = "1.3.0"
val recyclerview = "androidx.recyclerview:recyclerview:$recyclerviewVersion"

val daggerVersion = "2.45"
val dagger = "com.google.dagger:dagger:$daggerVersion"

val junitVersion = "4.13.2"
val junit = "junit:junit:$junitVersion"

dependencies {
    implementation(androidXCore)
    implementation(cardView)
    implementation(recyclerview)
    implementation(dagger)

    testImplementation(junit)
}
