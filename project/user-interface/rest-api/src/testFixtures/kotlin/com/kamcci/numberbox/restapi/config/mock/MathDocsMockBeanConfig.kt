package com.kamcci.numberbox.restapi.config.mock

import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperReadCase
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperWriteCase
import com.kamcci.numberbox.app.usecase.docs.MathDocsReadCase
import com.kamcci.numberbox.app.usecase.docs.MathDocsUsageWriteCase
import org.mockito.Mockito
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class MathDocsMockBeanConfig {
    @Bean
    fun mathDocsPaperReadCase(): MathDocsPaperReadCase = Mockito.mock()

    @Bean
    fun mathDocsPaperWriteCase(): MathDocsPaperWriteCase = Mockito.mock()

    @Bean
    fun mathDocsReadCase(): MathDocsReadCase = Mockito.mock()

    @Bean
    fun mathDocsUsageWriteCase(): MathDocsUsageWriteCase = Mockito.mock()
}