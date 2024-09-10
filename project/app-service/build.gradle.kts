import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"

    kotlin("kapt") version "1.9.22"
    kotlin("plugin.allopen") version "1.9.22"
    kotlin("plugin.noarg") version "1.9.22"
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":project:app-domain"))
}

allOpen {
    annotation("com.kamcci.numberbox.app.domain.system_construction.UseCase")
}

noArg {
    annotation("com.kamcci.numberbox.app.domain.system_construction.UseCase")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}