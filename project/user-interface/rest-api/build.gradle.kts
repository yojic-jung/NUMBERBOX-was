import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java-test-fixtures")
    id("jacoco")

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

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.springframework.boot:spring-boot-starter-validation")

    // kotlin 직렬화(data class request 생성자 오류 해결)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")

    // ppt
    implementation("org.apache.poi:poi-ooxml:5.2.5")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
    testImplementation("io.mockk:mockk:1.13.8")

    testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")
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

tasks.withType<Test> {
    useJUnitPlatform()
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
            exclude("**/*MapperImpl.class")
        }
    )
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            excludes = mutableListOf("**/*MapperImpl.class")
        }
    }
}
