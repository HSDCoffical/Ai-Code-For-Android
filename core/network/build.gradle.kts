plugins {
    id 'com.android.library'
    id 'kotlin-android'
}

android {
    namespace 'com.example.core.network'
    compileSdk 34
}

dependencies {
    implementation project(':core:model')
    implementation 'com.squareup.retrofit2:retrofit:2.11.0'
    implementation 'com.squareup.retrofit2:converter-moshi:2.11.0'
    implementation 'com.squareup.okhttp3:okhttp:4.12.0'
    implementation 'com.squareup.moshi:moshi:1.15.0'
}