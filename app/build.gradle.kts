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
            isMinifyEnabled = false
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
}

val powerAdapterProject = ":poweradapter"

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

val rxJavaVersion = "2.2.9"
val rxJava = "io.reactivex.rxjava2:rxjava:$rxJavaVersion"

val rxAndroidVersion = "2.1.1"
val rxAndroid = "io.reactivex.rxjava2:rxandroid:$rxAndroidVersion"

val rxRelayVersion = "2.1.0"
val rxRelay = "com.jakewharton.rxrelay2:rxrelay:$rxRelayVersion"

val rxBindingVersion = "2.2.0"
val rxBinding = "com.jakewharton.rxbinding2:rxbinding-kotlin:$rxBindingVersion"

val threeTenVersion = "1.2.1"
val threeTen = "com.jakewharton.threetenabp:threetenabp:$threeTenVersion"

val timberVersion = "5.0.1"
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

    implementation(project(powerAdapterProject))

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
