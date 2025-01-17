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
        toolVersion = "0.8.12"
    }

    tasks.jacocoTestReport {
        onlyIf { project.name != "app-domain" } // app-domain 모듈 제외
        reports {
            html.required.set(true)
            xml.required.set(true)

            xml.outputLocation.set(file(layout.buildDirectory.dir("reports/jacoco/test/jacocoTestReport.xml")))
            html.outputLocation.set(file(layout.buildDirectory.dir("reports/jacoco/test/jacocoTestReport.html")))
        }
        classDirectories.setFrom(
            sourceSets.main.get().output.asFileTree.matching {
                exclude(
                    "**/*MapperImpl.class",
                    "**/Q*Entity.class",
                    "**/Q*Domain.class",
                )
            }
        )
    }
}

sonarqube {
    properties {
        property("sonar.projectKey", "numberbox-was")
        property("sonar.projectName", "numberbox-was")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.token", project.findProperty("sonar.token") ?: "")
        // Java 커버리지 리포트 경로
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
        // Kotlin 커버리지 리포트 경로
//        property("sonar.kotlin.coverage.reportPaths", "build/reports/kover/xml/report.xml")
        property("sonar.coverage.exclusions", "**/com/kamcci/numberbox/app/domain/**")

    }
}
