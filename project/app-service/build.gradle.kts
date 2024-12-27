import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-test-fixtures")

    kotlin("jvm")
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

    testImplementation(libs.bundles.app.service.test)
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

tasks.jacocoTestReport {
    dependsOn(":project:app-service:test")

    classDirectories.setFrom(
        files(
            fileTree("${buildDir}/classes/kotlin/main") {
                include("**/*.class")
            },
            fileTree("${project(":project:app-domain").buildDir}/classes/kotlin/main") {
                include("**/*.class")
            }
        )
    )

    executionData.setFrom(
        file("${buildDir}/jacoco/test.exec")
    )

    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}