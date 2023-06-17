plugins {
    id("com.android.application") version "8.0.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
}

ext {
    extra["versionCode"] = 1
    extra["versionName"] = "1.0"
    extra["applicationId"] = "com.nitrosoft.ua.advancedandroid"
    extra["minSdk"] = 24
    extra["compileSdk"] = 33
    extra["targetSdk"] = 33

    val androidXCoreVersion = "1.10.1"
    extra["androidXCore"] = "androidx.core:core:$androidXCoreVersion"

    val cardViewVersion = "1.0.0"
    extra["cardView"] = "androidx.cardview:cardview:$cardViewVersion"

    val daggerVersion = "2.45"
    extra["dagger"] = "com.google.dagger:dagger:$daggerVersion"
    extra["daggerCompiler"] = "com.google.dagger:dagger-compiler:$daggerVersion"
    extra["daggerSupport"] = "com.google.dagger:dagger-android-support:$daggerVersion"

    val glideVersion = "4.9.0"
    extra["glide"] = "com.github.bumptech.glide:glide:$glideVersion"
    extra["glideCompiler"] = "com.github.bumptech.glide:compiler:$glideVersion"

    @Suppress("SpellCheckingInspection")
    val kotshiVersion = "2.10.2"
    @Suppress("SpellCheckingInspection")
    extra["kotshi"] = "se.ansman.kotshi:api:$kotshiVersion"
    @Suppress("SpellCheckingInspection")
    extra["kotshiCompiler"] = "se.ansman.kotshi:compiler:$kotshiVersion"

    val materialVersion = "1.9.0"
    extra["material"] = "com.google.android.material:material:$materialVersion"

    val okhttp3LoggingVersion = "4.1.0"
    extra["okhttp3Logging"] = "com.squareup.okhttp3:logging-interceptor:$okhttp3LoggingVersion"

    val recyclerviewVersion = "1.3.0"
    extra["recyclerview"] = "androidx.recyclerview:recyclerview:$recyclerviewVersion"

    val retrofitVersion = "2.6.1"
    extra["retrofit"] = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    extra["retrofitConvertMoshi"] = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
    extra["retrofitRxJavaAdapter"] = "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"

    val roomVersion = "2.5.1"
    extra["room"] = "androidx.room:room-runtime:$roomVersion"
    extra["roomCompiler"] = "androidx.room:room-runtime:$roomVersion"
    extra["roomRxJava"] = "androidx.room:room-rxjava2:$roomVersion"

    val rxJavaVersion = "2.2.9"
    extra["rxJava"] = "io.reactivex.rxjava2:rxjava:$rxJavaVersion"

    val rxAndroidVersion = "2.1.1"
    extra["rxAndroid"] = "io.reactivex.rxjava2:rxandroid:$rxAndroidVersion"

    val rxRelayVersion = "2.1.0"
    extra["rxRelay"] = "com.jakewharton.rxrelay2:rxrelay:$rxRelayVersion"

    val rxBindingVersion = "2.2.0"
    extra["rxBinding"] = "com.jakewharton.rxbinding2:rxbinding-kotlin:$rxBindingVersion"

    val threeTenVersion = "1.2.1"
    extra["threeTen"] = "com.jakewharton.threetenabp:threetenabp:$threeTenVersion"

    val timberVersion = "5.0.1"
    extra["timber"] = "com.jakewharton.timber:timber:$timberVersion"

    val junitVersion = "4.13.2"
    extra["junit"] = "junit:junit:$junitVersion"

    val mockitoCoreVersion = "3.0.0"
    extra["mockitoCore"] = "org.mockito:mockito-core:$mockitoCoreVersion"

    val testCoreVersion = "1.5.0"
    extra["testCore"] = "androidx.test:core:$testCoreVersion"

    val testRunnerVersion = "1.5.0"
    extra["testRunner"] = "androidx.test:runner:$testRunnerVersion"

    val testRulesVersion = "1.3.0"
    extra["testRules"] = "androidx.test:rules:$testRulesVersion"

    val espressoCoreVersion = "3.3.0"
    extra["espressoCore"] = "androidx.test.espresso:espresso-core:$espressoCoreVersion"
}