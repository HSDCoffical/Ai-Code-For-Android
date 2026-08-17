plugins {
    id 'java-library'
    id 'kotlin'
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation 'com.squareup.moshi:moshi:1.15.0'
}