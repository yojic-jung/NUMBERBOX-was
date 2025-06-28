import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.22"
}

group = "com.numberbox"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":modules:auth-engine"))
    implementation(project(":modules:mail-sender-engine"))
    implementation(project(":modules:logging-engine"))
    implementation(project(":modules:system-construction"))

    implementation(project(":project:app-domain"))
    implementation(project(":project:app-service"))
    implementation(project(":project:infrastructure:email-adapter"))
    implementation(project(":project:infrastructure:orm-jpa-adapter"))
    implementation(project(":project:infrastructure:persistence-adapter"))
    implementation(project(":project:infrastructure:storage-adapter"))
    implementation(project(":project:infrastructure:hwp-client-adapter"))
    implementation(project(":project:user-interface:rest-api"))

    implementation(libs.bundles.boot.strap)
    testImplementation(libs.bundles.boot.strap.test)
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}
