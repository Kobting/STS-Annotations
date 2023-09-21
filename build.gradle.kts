import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.22"
    id("com.google.devtools.ksp") version "1.7.22-1.0.8"
    id("com.github.kobting")
}

group = "com.github.kobting"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":annotations"))
    implementation(project.ext.get("SlayTheSpire")!!)
    implementation(project.ext.get("BaseMod")!!)
    implementation(project.ext.get("ModTheSpire")!!)
    ksp(project(":processors"))
}

kotlin {
    sourceSets {
        getByName("main") {
            kotlin.srcDir("build/generated/ksp/main/kotlin")
        }
    }
}

ksp {
    arg("resourceBasePath", "kobting/test-mod")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}