import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    maven
    id("com.github.kobting")
}

group = "com.github.kobting.sts-annotations"
version = "0.8.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(project(":annotations"))
    implementation(project.ext.get("ModTheSpire")!!)
    implementation(project.ext.get("SlayTheSpire")!!)
    implementation(project.ext.get("BaseMod")!!)
    implementation("com.google.devtools.ksp:symbol-processing-api:1.7.22-1.0.8")
    implementation("com.squareup:kotlinpoet-ksp:1.12.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}