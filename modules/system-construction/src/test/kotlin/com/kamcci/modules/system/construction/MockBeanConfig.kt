package com.kamcci.modules.system.construction

import com.kamcci.modules.system.construction.di.config.AnnotationBeanFactoryPostProcessor
import org.mockito.Mockito
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.transaction.PlatformTransactionManager

@TestConfiguration
class MockBeanConfig {
    @Bean
    fun platformTransactionManager(): PlatformTransactionManager = Mockito.mock()

    @Bean
    fun annotationBeanFactoryPostProcessor() = AnnotationBeanFactoryPostProcessor(Mockito.mock(), Mockito.mock())
}