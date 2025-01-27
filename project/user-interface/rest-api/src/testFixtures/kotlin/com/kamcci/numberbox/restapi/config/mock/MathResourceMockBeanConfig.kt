package com.kamcci.numberbox.restapi.config.mock

import com.kamcci.numberbox.app.usecase.resource.MathResourceMenuReadCase
import com.kamcci.numberbox.app.usecase.resource.MathResourceReadCase
import com.kamcci.numberbox.app.usecase.resource.MathResourceWriteCase
import org.mockito.Mockito.mock
import org.springframework.context.annotation.Bean

class MathResourceMockBeanConfig {
    @Bean
    fun mathResourceMenuReadCase(): MathResourceMenuReadCase = mock()

    @Bean
    fun mathResourceReadCase(): MathResourceReadCase = mock()

    @Bean
    fun mathResourceWriteCase(): MathResourceWriteCase = mock()
}