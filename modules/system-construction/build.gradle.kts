import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java-test-fixtures")
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.22"
}

group = "com.kamcci"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.modules.system.construction)
    testImplementation(libs.bundles.modules.system.construction.test)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<BootRun>("bootRun") {
    enabled = false
}
