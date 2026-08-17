pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "MyAIChat"
include(":app")
include(":core:model", ":core:network", ":core:data")
include(":feature:chat", ":feature:settings")