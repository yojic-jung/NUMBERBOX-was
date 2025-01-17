pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.jvm") version "1.9.22"
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "NUMBERBOX-was"

// 프로젝트 모듈 포함
include(":project")

// project-bootstrap
include(":project:bootstrap")

// project-app
include(":project:app-domain")
include(":project:app-service")

// project-adapter
include(":project:infrastructure")
include(":project:infrastructure:orm-jpa-adapter")
include(":project:infrastructure:storage-adapter")
include(":project:infrastructure:email-adapter")
include(":project:infrastructure:hwp-client-adapter")

// project-ui
include(":project:user-interface")
include(":project:user-interface:rest-api")

// modules
include(":modules")
include(":modules:auth-control")
include(":modules:auth-engine")
include(":modules:mail-sender-control")
include(":modules:mail-sender-engine")
include(":modules:logging-control")
include(":modules:logging-engine")
include(":modules:system-construction")
