plugins {
    id("java-library")  // 改为 java-library
    id("org.jetbrains.kotlin.jvm")  // 添加 jvm 插件
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
dependencies {
    implementation("com.squareup.moshi:moshi:1.15.0")
}