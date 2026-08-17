plugins {
    id("com.android.library")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}
android {
    namespace = "com.example.core.data"
    compileSdk = 34
}
dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
}