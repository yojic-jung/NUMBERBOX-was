package com.kamcci.numberbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = ["com.kamcci.modules", "com.kamcci.numberbox"])
@SpringBootApplication
class BootstrapApplicaton

fun main(args: Array<String>) {
    runApplication<BootstrapApplicaton>(*args)
}
