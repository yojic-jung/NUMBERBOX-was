import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java-test-fixtures")
    id("jacoco")

    kotlin("jvm")
    kotlin("kapt") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    kotlin("plugin.allopen") version "1.9.22"
    kotlin("plugin.noarg") version "1.9.22"
}

group = "com.numberbox"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
    annotation("org.springframework.stereotype.Repository")
}

noArg {
    annotation("jakarta.persistence.Entity")
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

    // jpa, querydsl
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

    // mysql
    implementation("com.mysql:mysql-connector-j")
    // java-uuid-generator
    implementation("com.fasterxml.uuid:java-uuid-generator:4.0.1")

    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // test
    testImplementation("org.flywaydb:flyway-core")
    testImplementation("org.flywaydb:flyway-mysql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")
    testFixturesImplementation("org.flywaydb:flyway-core")
    testFixturesImplementation("org.flywaydb:flyway-mysql")

    testFixturesImplementation("org.springframework.boot:spring-boot-testcontainers")
    testFixturesImplementation("org.testcontainers:mysql")
    testFixturesImplementation("org.testcontainers:jdbc")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<BootRun>("bootRun") {
    enabled = false
}


jacoco {
    toolVersion = "0.8.11"
}

var Qdomains = mutableListOf<String>()

for (qPattern in 'A'..'Z') {
    Qdomains.add("*.Q${qPattern}*")
}

tasks.jacocoTestReport {
    executionData(fileTree(layout.buildDirectory).include("jacoco/*.exec"))
    reports {
        html.required.set(true)
        xml.required.set(true)

        html.outputLocation = file("${layout.buildDirectory}/jacoco.html")
        xml.outputLocation = file("${layout.buildDirectory}/jacoco.xml")
    }

    classDirectories.setFrom(
        sourceSets.main.get().output.asFileTree.matching {
            exclude("**/Q*Entity.class", "**/Q*Domain.class")
        }
    )
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            excludes = mutableListOf("**/Q*Entity")
        }
    }
}
