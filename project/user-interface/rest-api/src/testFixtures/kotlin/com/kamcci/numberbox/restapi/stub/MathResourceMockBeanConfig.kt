package com.kamcci.numberbox.restapi.stub

import com.kamcci.numberbox.app.service.stub.usecase.resource.MockMathResourceMenuReadCase
import com.kamcci.numberbox.app.service.stub.usecase.resource.MockMathResourceReadCase
import com.kamcci.numberbox.app.service.stub.usecase.resource.MockMathResourceWriteCase
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