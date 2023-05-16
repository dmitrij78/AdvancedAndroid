plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.nitrosoft.ua.advancedandroid"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.nitrosoft.ua.advancedandroid"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            @Suppress("UnstableApiUsage")
            isMinifyEnabled = false
            @Suppress("UnstableApiUsage")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
}

val daggerVersion = "2.45"
val dagger = "com.google.dagger:dagger:$daggerVersion"
val daggerCompiler = "com.google.dagger:dagger-compiler:$daggerVersion"
val daggerSupport = "com.google.dagger:dagger-android-support:$daggerVersion"

val androidXCoreVersion = "1.10.1"
val androidXCore = "androidx.core:core:$androidXCoreVersion"

val cardViewVersion = "1.0.0"
val cardView = "androidx.cardview:cardview:$cardViewVersion"

val recyclerviewVersion = "1.3.0"
val recyclerview = "androidx.recyclerview:recyclerview:$recyclerviewVersion"

val materialVersion = "1.9.0"
val material = "com.google.android.material:material:$materialVersion"

val roomVersion = "2.5.1"
val room = "androidx.room:room-runtime:$roomVersion"
val roomCompiler = "androidx.room:room-runtime:$roomVersion"
val roomRxJava = "androidx.room:room-rxjava2:$roomVersion"

val okhttp3LoggingVersion = "4.1.0"
val okhttp3Logging = "com.squareup.okhttp3:logging-interceptor:$okhttp3LoggingVersion"

val retrofitVersion = "2.6.1"
val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
val retrofitConvertMoshi = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
val retrofitRxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"

@Suppress("SpellCheckingInspection")
val kotshiVersion = "2.10.2"

@Suppress("SpellCheckingInspection")
val kotshi = "se.ansman.kotshi:api:$kotshiVersion"

@Suppress("SpellCheckingInspection")
val kotshiCompiler = "se.ansman.kotshi:compiler:$kotshiVersion"

val rxJavaVersion = "1.2.1"
val rxJava = "io.reactivex.rxjava2:rxjava:$rxJavaVersion"

val rxAndroidVersion = "2.1.1"
val rxAndroid = "io.reactivex.rxjava2:rxandroid:$rxAndroidVersion"

val rxRelayVersion = "2.1.0"
val rxRelay = "com.jakewharton.rxrelay2:rxrelay:$rxRelayVersion"

val rxBindingVersion = "2.2.0"
val rxBinding = "com.jakewharton.rxbinding2:rxbinding-kotlin:$rxBindingVersion"

val threeTenVersion = "1.2.1"
val threeTen = "com.jakewharton.threetenabp:threetenabp:$threeTenVersion"

val timberVersion = "1.2.1"
val timber = "com.jakewharton.timber:timber:$timberVersion"

val glideVersion = "4.9.0"
val glide = "com.github.bumptech.glide:glide:$glideVersion"
val glideCompiler = "com.github.bumptech.glide:compiler:$glideVersion"

val junitVersion = "4.13.2"
val junit = "junit:junit:$junitVersion"

val mockitoCoreVersion = "3.0.0"
val mockitoCore = "org.mockito:mockito-core:$mockitoCoreVersion"

val testCoreVersion = "1.5.0"
val testCore = "androidx.test:core:$testCoreVersion"

val testRunnerVersion = "1.5.0"
val testRunner = "androidx.test:runner:$testRunnerVersion"

val testRulesVersion = "1.3.0"
val testRules = "androidx.test:rules:$testRulesVersion"

val espressoCoreVersion = "3.3.0"
val espressoCore = "androidx.test.espresso:espresso-core:$espressoCoreVersion"

dependencies {

    implementation(project(":poweradapter"))

    implementation(androidXCore)
    implementation(cardView)
    implementation(recyclerview)
    implementation(material)

    implementation(room)
    kapt(roomCompiler)

    implementation(roomRxJava)

    implementation(dagger)
    implementation(daggerSupport)
    kapt(daggerCompiler)

    implementation(okhttp3Logging)

    implementation(retrofit)
    implementation(retrofitConvertMoshi)
    implementation(retrofitRxJavaAdapter)

    implementation(kotshi)
    kapt(kotshiCompiler)

    implementation(threeTen)

    implementation(rxJava)
    implementation(rxAndroid)
    implementation(rxRelay)
    implementation(rxBinding)

    implementation(timber)

    implementation(glide)
    kapt(glideCompiler)

    testImplementation(junit)

    testImplementation(mockitoCore)

    kaptAndroidTest(daggerCompiler)

    androidTestImplementation(testCore)

    androidTestImplementation(testRunner)
    androidTestImplementation(testRules)

    androidTestImplementation(espressoCore)
}


//plugins {
//    id("com.android.application")
//    id("org.jetbrains.kotlin.android")
//    id("kotlin-kapt")
//}
//
//android {
//    compileSdkVersion(33)
//    defaultConfig {
//        applicationId = rootProject.ext.applicationId
//        minSdkVersion(rootProject.ext.minSdkVersion)
//        targetSdkVersion(rootProject.ext.targetSdkVersion)
//        versionCode = rootProject.ext.versionCode
//        versionName = rootProject.ext.versionName
//        testInstrumentationRunner = "com.nitrosoft.ua.advancedandroid.test.CustomTestRunner"
//    }
//    buildTypes {
//        getByName("release") {
//            isMinifyEnabled = true
//            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
//        }
//        getByName("debug") {
//            applicationIdSuffix = ".debug"
//            isDebuggable = true
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//}
//
////kapt {
////    arguments {
////        // When using Java 9 and above
////        arg("kotshi.generatedAnnotation", "javax.annotation.processing.Generated")
////    }
////}
//
//dependencies {
//    implementationproject(":poweradapter")
//
//    implementation("androidx.core:core:$androidxVersion")
//    implementation("androidx.cardview:cardview:$cardViewVersion")
//    implementation("androidx.recyclerview:recyclerview:$recylerViewVersion")
//    implementation("com.google.android.material:material:$materialVersion")
//
//    implementation("androidx.room:room-runtime:$archComponentVersion")
//    kapt("androidx.room:room-compiler:$archComponentVersion")
//
//    implementation("androidx.room:room-rxjava2:$archComponentVersion")
//
//    implementation("com.google.dagger:dagger:$daggerVersion")
//    implementation("com.google.dagger:dagger-android-support:$daggerVersion")
//    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
//
//    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
//    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
//    implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")
//    implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")
//
//    implementation("se.ansman.kotshi:api:$kotshiVersion")
//    kapt("se.ansman.kotshi:compiler:$kotshiVersion")
//
//    implementation("com.jakewharton.threetenabp:threetenabp:$treeTenAppVersion")
//
//    implementation("io.reactivex.rxjava2:rxjava:$rxJavaVersion")
//    implementation("io.reactivex.rxjava2:rxandroid:$rxAndroidVersion")
//    implementation("com.jakewharton.rxrelay2:rxrelay:$rxRelayVersion")
//    implementation("com.jakewharton.rxbinding2:rxbinding-kotlin:$rxBindingsVersion")
//
//    implementation("com.jakewharton.timber:timber:$timberVersion")
//
//    implementation("com.github.bumptech.glide:glide:$glideVersion")
//    kapt("com.github.bumptech.glide:compiler:$glideVersion")
//
//    testimplementation("junit:junit:$junitVersion")
//    testimplementation("org.mockito:mockito-core:$mockitoVersion")
//
//    kaptAndroidTest ("com.google.dagger:dagger-compiler:$daggerVersion")
//
//    androidTestimplementation("androidx.test:core:$testCoreVersion")
//
//    androidTestimplementation("androidx.test:runner:$testRunnerVersion")
//    androidTestimplementation("androidx.test:rules:$testRulesVersion")
//    androidTestimplementation("androidx.test.espresso:espresso-core:$espressoVersion")
//}

//def mockCopySpec = copySpec {
//    from rootProject . file ('app/')
//    include 'mock/'
//}
//
//task copyMock (type: Copy) {
//    ['src/debug/assets', 'src/test/resources', 'src/androidTest/resources'].each { dest ->
//        copy {
//            with mockCopySpec
//                    into dest
//        }
//    }
//}

//build.finalizedBy(copyMock)
