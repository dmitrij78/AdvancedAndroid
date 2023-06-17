plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.nitrosoft.ua.advancedandroid"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        versionCode = rootProject.extra["versionCode"] as Int
        versionName = rootProject.extra["versionName"] as String
        applicationId = rootProject.extra["applicationId"] as String
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["targetSdk"] as Int
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            @Suppress("UnstableApiUsage")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    lint {
        abortOnError = false
    }
}

dependencies {
    implementation(project(":poweradapter"))

    implementation(rootProject.extra["androidXCore"] as String)
    implementation(rootProject.extra["cardView"] as String)
    implementation(rootProject.extra["recyclerview"] as String)
    implementation(rootProject.extra["material"] as String)

    implementation(rootProject.extra["room"] as String)
    kapt(rootProject.extra["roomCompiler"] as String)

    implementation(rootProject.extra["roomRxJava"] as String)

    implementation(rootProject.extra["dagger"] as String)
    implementation(rootProject.extra["daggerSupport"] as String)
    kapt(rootProject.extra["daggerCompiler"] as String)

    implementation(rootProject.extra["okhttp3Logging"] as String)

    implementation(rootProject.extra["retrofit"] as String)
    implementation(rootProject.extra["retrofitConvertMoshi"] as String)
    implementation(rootProject.extra["retrofitRxJavaAdapter"] as String)

    implementation(rootProject.extra["kotshi"] as String)
    kapt(rootProject.extra["kotshiCompiler"] as String)

    implementation(rootProject.extra["threeTen"] as String)

    implementation(rootProject.extra["rxJava"] as String)
    implementation(rootProject.extra["rxAndroid"] as String)
    implementation(rootProject.extra["rxRelay"] as String)
    implementation(rootProject.extra["rxBinding"] as String)

    implementation(rootProject.extra["timber"] as String)

    implementation(rootProject.extra["glide"] as String)
    kapt(rootProject.extra["glideCompiler"] as String)

    testImplementation(rootProject.extra["junit"] as String)

    testImplementation(rootProject.extra["mockitoCore"] as String)

    kaptAndroidTest(rootProject.extra["daggerCompiler"] as String)

    androidTestImplementation(rootProject.extra["testCore"] as String)

    androidTestImplementation(rootProject.extra["testRunner"] as String)
    androidTestImplementation(rootProject.extra["testRules"] as String)

    androidTestImplementation(rootProject.extra["espressoCore"] as String)
}

tasks.register("copyMock") {
    val mockCopySpec = copySpec {
        from(rootProject.file("app/"))
        include("mock/")
    }
    listOf(
        "src/debug/assets",
        "src/test/resources",
        "src/androidTest/resources"
    ).forEach { destination ->
        copy {
            with(mockCopySpec)
            into(destination)
        }
    }
}

tasks.build {
    finalizedBy("copyMock")
}
