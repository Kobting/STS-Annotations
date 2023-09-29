import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    maven
}

group = "com.github.kobting.sts-annotations"
version = "0.8.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}