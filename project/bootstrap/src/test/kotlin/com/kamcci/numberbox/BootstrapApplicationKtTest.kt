package com.kamcci.numberbox

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.BeanCreationException
import org.springframework.boot.builder.SpringApplicationBuilder

class BootstrapApplicationKtTest {
    @Test
    fun testRunApplicationWithArgs() {
        val args = arrayOf("--spring.profiles.active=")
        val context = SpringApplicationBuilder(BootstrapApplication::class.java)

        assertThrows<BeanCreationException> {
            context.run(*args)
        }

    }
}