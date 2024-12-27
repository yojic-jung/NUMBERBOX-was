plugins {
    id("java")
    id("jacoco")
    id("jacoco-report-aggregation")
    id("org.sonarqube") version "5.1.0.4882"
}

group = "com.numberbox"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "jacoco")

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        finalizedBy("jacocoTestReport")
    }

    jacoco {
        toolVersion = "0.8.11"
    }

    tasks.jacocoTestReport {
        dependsOn(tasks.test)

        reports {
            html.required.set(true)
            xml.required.set(true)

            html.outputLocation = file("${layout.buildDirectory}/reports/jacoco.html")
            xml.outputLocation = file("${layout.buildDirectory}/reports/jacoco.xml")
        }
        classDirectories.setFrom(
            sourceSets.main.get().output.asFileTree.matching {
                exclude("**/*MapperImpl.class", "**/Q*Entity.class", "**/Q*Domain.class")
            }
        )

        // 실행된 모든 `jacoco.exec` 파일 병합
        executionData.setFrom(fileTree(projectDir).matching {
            this.include("**/jacoco.exec")
        })
    }
}

sonarqube {
    properties {
        property("sonar.projectKey", "numberbox-was")
        property("sonar.projectName", "numberbox-was")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.token", project.findProperty("sonar.token") ?: "")
    }
}
