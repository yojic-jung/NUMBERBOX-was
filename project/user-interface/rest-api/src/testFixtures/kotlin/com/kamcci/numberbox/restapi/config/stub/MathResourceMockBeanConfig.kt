package com.kamcci.numberbox.restapi.config.stub

import com.kamcci.numberbox.app.service.mock.usecase.resource.MockMathResourceMenuReadCase
import com.kamcci.numberbox.app.service.mock.usecase.resource.MockMathResourceReadCase
import com.kamcci.numberbox.app.service.mock.usecase.resource.MockMathResourceWriteCase
import com.kamcci.numberbox.app.usecase.resource.MathResourceMenuReadCase
import com.kamcci.numberbox.app.usecase.resource.MathResourceReadCase
import com.kamcci.numberbox.app.usecase.resource.MathResourceWriteCase
import org.springframework.context.annotation.Bean

class MathResourceMockBeanConfig {
    @Bean
    fun mathResourceMenuReadCase(): MathResourceMenuReadCase = MockMathResourceMenuReadCase()

    @Bean
    fun mathResourceReadCase(): MathResourceReadCase = MockMathResourceReadCase()

    @Bean
    fun mathResourceWriteCase(): MathResourceWriteCase = MockMathResourceWriteCase()
}