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
}

rootProject.name = "kobting-sts-auto-descriptions"
include(":annotations")
include(":processors")
