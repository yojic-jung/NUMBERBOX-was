package com.kamcci.numberbox.restapi.config.mock

import com.kamcci.numberbox.app.usecase.math.MathCategoryTypeReadCase
import com.kamcci.numberbox.app.usecase.math.MathCategoryUnitReadCase
import com.kamcci.numberbox.app.usecase.math.MathFormulaKeyReadCase
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class MathCategoryMockBeanConfig {
    @Bean
    fun mathCategoryUnitReadCase(): MathCategoryUnitReadCase = mock()

    @Bean
    fun mathCategoryTypeReadCase(): MathCategoryTypeReadCase = mock()

    @Bean
    fun mathFormulaKeyReadCase(): MathFormulaKeyReadCase = mock()

}