import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.konan.properties.loadProperties
import java.util.*

plugins {
    kotlin("jvm") version "1.7.22"
    id("com.google.devtools.ksp") version "1.7.22-1.0.8"
    id("sts-dependencies.dependencies")
}

group = "com.github.kobting"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":annotations"))
    implementation(ModTheSpire)
    implementation(SlayTheSpire)
    implementation(BaseMod)
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

val keys = try {
    loadProperties("${rootProject.projectDir.path}/keys/keys.properties")
} catch(ex: Exception) {
    ex.printStackTrace()
    logger.warn("You will not be able to publish")
    Properties()
}

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "java") //required for publications components
    configure<PublishingExtension> {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/kobting/STS-Annotations")
                credentials {
                    username = keys.getOrDefault("username", "").toString()
                    password = keys.getOrDefault("mavenDeploy", "").toString()
                }
            }
        }
        publications {
            register<MavenPublication>("gpr") {
                from(components["java"])
            }
        }
    }
}