package com.kamcci.numberbox.restapi.config.mock

import com.kamcci.numberbox.app.port.orm.math.MathContentsReadOrmPort
import com.kamcci.numberbox.app.usecase.math.*
import com.kamcci.numberbox.restapi.mapper.math.MathContentsMapper
import org.mockito.Mockito
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class MathContentsMockBeanConfig {
    @Bean
    fun mathContentsIpsiReadCase(): MathContentsIpsiReadCase = Mockito.mock()

    @Bean
    fun mathContentsReadCase(): MathContentsReadCase = Mockito.mock()

    @Bean
    fun mathContentsWriteCase(): MathContentsWriteCase = Mockito.mock()

    @Bean
    fun mathConGrammarModifyUseCase(): MathContentsGrammarWriteCase = Mockito.mock()

    @Bean
    fun mathContentsMapper(): MathContentsMapper = Mockito.mock()

    @Bean
    fun mathContentsRepoReadCase(): MathContentsRepoReadCase = Mockito.mock()

    @Bean
    fun mathConLikeModifyUseCase(): MathContentsLikeWriteCase = Mockito.mock()

    @Bean
    fun mathConRepoModifyUseCase(): MathContentsRepoWriteCase = Mockito.mock()

    @Bean
    fun mathConLikeReadUseCase(): MathContentsLikeReadCase = Mockito.mock()

    @Bean
    fun mathContentsReadOrmPort(): MathContentsReadOrmPort = Mockito.mock()
}