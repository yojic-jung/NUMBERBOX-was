package com.numberbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = arrayOf("com.numberbox", "com.kamcci"))
@SpringBootApplication
class BootstrapApplicaton

fun main(args: Array<String>) {
    runApplication<BootstrapApplicaton>(*args)
}
