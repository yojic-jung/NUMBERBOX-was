import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java-test-fixtures")

    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.22"
    kotlin("kapt") version "1.9.22"
}

group = "com.hexagonal"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation(project(":project:app-domain"))
    implementation(project(":project:app-service"))
    implementation(project(":modules:auth-control"))

    implementation(libs.bundles.ui.rest.api)
    kapt(libs.mapstruct.processor)

    testImplementation(libs.bundles.ui.rest.api.test)
    testFixturesImplementation(libs.bundles.ui.rest.api.test.fixture)

    // end to end test config
    testImplementation(project(":project:app-domain"))
    testImplementation(project(":project:app-service"))
    testImplementation(project(":project:infrastructure:storage-adapter"))
    testImplementation(project(":project:infrastructure:email-adapter"))
    testImplementation(project(":project:infrastructure:orm-jpa-adapter"))
    testImplementation(testFixtures(project(":project:app-service")))
    testImplementation(testFixtures(project(":project:infrastructure:orm-jpa-adapter")))
    testImplementation(project(":modules:system-construction"))
    testFixturesImplementation(testFixtures(project(":project:app-service")))
    testFixturesImplementation(project(":modules:mail-sender-control"))
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

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<BootRun>("bootRun") {
    enabled = false
}