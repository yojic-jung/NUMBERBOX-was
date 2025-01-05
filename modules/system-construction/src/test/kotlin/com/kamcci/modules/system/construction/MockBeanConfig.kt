package com.kamcci.modules.system.construction

import org.mockito.Mockito
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.transaction.PlatformTransactionManager

@TestConfiguration
class MockBeanConfig {
    @Bean
    fun platformTransactionManager(): PlatformTransactionManager = Mockito.mock()
}