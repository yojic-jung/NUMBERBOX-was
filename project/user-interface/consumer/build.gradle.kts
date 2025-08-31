import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.22"

    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java-test-fixtures")
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/release") }
}


java {
    sourceCompatibility = JavaVersion.VERSION_17
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.0")
    }
}

dependencies {
    implementation(project(":project:app-domain"))
    implementation(project(":project:app-service"))
    api("org.springframework.cloud:spring-cloud-starter-stream-kafka:4.0.4")

    implementation(libs.bundles.ui.consumer)
    testImplementation(libs.bundles.ui.consumer.test)
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
