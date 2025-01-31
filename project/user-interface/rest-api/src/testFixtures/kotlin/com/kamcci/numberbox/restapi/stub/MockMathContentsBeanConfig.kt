package com.kamcci.numberbox.restapi.stub

import com.kamcci.numberbox.app.domain.dto.math.MathContentsModifyDto
import com.kamcci.numberbox.app.service.stub.usecase.math.*
import com.kamcci.numberbox.app.usecase.math.*
import com.kamcci.numberbox.restapi.dto.request.math.MathContentsModifyRequest
import com.kamcci.numberbox.restapi.mapper.math.MathContentsMapper
import org.springframework.context.annotation.Bean
import java.util.*

class MockMathContentsBeanConfig {
    @Bean
    fun mathCategoryTypeReadCase(): MathCategoryTypeReadCase = MockMathCategoryTypeReadCase()

    @Bean
    fun mathCategoryUnitReadCase(): MathCategoryUnitReadCase = MockMathCategoryUnitReadCase()

    @Bean
    fun mathContentsGrammarWriteCase(): MathContentsGrammarWriteCase = MockMathContentsGrammarWriteCase()

    @Bean
    fun mathContentsIpsiReadCase(): MathContentsIpsiReadCase = MockMathContentsIpsiReadCase()

    @Bean
    fun mathContentsLikeReadCase(): MathContentsLikeReadCase = MockMathContentsLikeReadCase()

    @Bean
    fun mathContentsLikeWriteCase(): MathContentsLikeWriteCase = MockMathContentsLikeWriteCase()

    @Bean
    fun mathContentsReadCase(): MathContentsReadCase = MockMathContentsReadCase()

    @Bean
    fun mathContentsRepoReadCase(): MathContentsRepoReadCase = MockMathContentsRepoReadCase()

    @Bean
    fun mathContentsRepoWriteCase(): MathContentsRepoWriteCase = MockMathContentsRepoWriteCase()

    @Bean
    fun mathContentsWriteCase(): MathContentsWriteCase = MockMathContentsWriteCase()

    @Bean
    fun mathFormulaKeyReadCase(): MathFormulaKeyReadCase = MockMathFormulaKeyReadCase()

    @Bean
    fun mathContentsMapper(): MathContentsMapper = object : MathContentsMapper {
        override fun toContents(memberId: UUID, request: MathContentsModifyRequest): MathContentsModifyDto {
            TODO("Not yet implemented")
        }
    }
}