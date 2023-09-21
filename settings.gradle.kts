pluginManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
}

buildscript {
    repositories {
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        classpath("com.github.kobting:sts-dependencies:1.0.0")
    }
}

rootProject.name = "kobting-sts-auto-descriptions"
include(":annotations")
include(":processors")
