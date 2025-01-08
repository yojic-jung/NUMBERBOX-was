package com.kamcci.numberbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling

@ComponentScan(basePackages = ["com.kamcci.modules", "com.kamcci.numberbox"])
@EnableScheduling
@SpringBootApplication
class BootstrapApplication

fun main(args: Array<String>) {
    runApplication<BootstrapApplication>(*args)
}
