rootProject.name = "yamanai-ame-ha-nothing"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform") version "1.7.20"
        id("org.jetbrains.compose") version "1.2.1"
    }
}