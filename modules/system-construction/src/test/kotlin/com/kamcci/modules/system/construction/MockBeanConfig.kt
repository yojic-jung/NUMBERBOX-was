package com.kamcci.modules.system.construction

import com.kamcci.modules.system.construction.stub.common.MockPlatformTransactionManager
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class MockBeanConfig {
    @Bean
    fun platformTransactionManager() = MockPlatformTransactionManager()
}