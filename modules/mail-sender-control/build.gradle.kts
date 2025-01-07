import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
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
    dependsOn(":modules:auth-engine:test")

    classDirectories.setFrom(classDirectories.files.map {
        files(
            fileTree(it) {
                setDir("${buildDir}/classes/kotlin/main")
                include("**/*.class")
            },
            fileTree(it) {
                setDir("${project(":modules:mail-sender-control").buildDir}/kotlin/java/main")
                include("**/*.class")
            }
        )
    })

    executionData.setFrom(
        file("${project(":modules:auth-engine").buildDir}/jacoco/test.exec")  // engine 모듈의 테스트 데이터
    )

    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}


