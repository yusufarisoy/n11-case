object Versions {
    const val hilt = "2.50"
    const val room = "2.6.1"
    const val retrofit = "2.9.0"
    const val lifecycle = "2.4.1"
    const val coroutines = "1.5.2"
    const val navigationComponent = "2.7.6"
}

object Libs {
    // Ktx
    const val coreKtx = "androidx.core:core-ktx:1.12.0"
    const val fragment = "androidx.fragment:fragment-ktx:1.4.1"
    // AppCompat
    const val appCompat = "androidx.appcompat:appcompat:1.6.1"
    // Material
    const val material = "com.google.android.material:material:1.11.0"
    // ConstraintLayout
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
    // Gson
    const val gson = "com.google.code.gson:gson:2.8.7"
    // Lifecycle
    const val lifecycleRunTime = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.0-alpha02"
    const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    // Coroutines
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    // Hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    // NavigationComponent
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationComponent}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationComponent}"
    // Room
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    // Glide
    const val glide = "com.github.bumptech.glide:glide:4.16.0"
    // Test
    const val junit = "junit:junit:4.13.2"
    const val extJunit = "androidx.test.ext:junit:1.1.5"
    const val espressoCore = "androidx.test.espresso:espresso-core:3.5.1"
}
