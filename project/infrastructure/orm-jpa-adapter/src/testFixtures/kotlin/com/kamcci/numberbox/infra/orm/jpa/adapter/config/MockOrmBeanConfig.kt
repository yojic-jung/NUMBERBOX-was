package com.kamcci.numberbox.infra.orm.jpa.adapter.config

import com.kamcci.modules.logging.control.service.IPAddressService
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class MockOrmBeanConfig {
    @Bean
    fun ipAddressService(): IPAddressService = object : IPAddressService {
        override fun getIPAddress(): String {
            return "127.0.0.1"
        }

        override fun getPublicIPAddress(): String {
            return "127.0.0.1"
        }
    }
}