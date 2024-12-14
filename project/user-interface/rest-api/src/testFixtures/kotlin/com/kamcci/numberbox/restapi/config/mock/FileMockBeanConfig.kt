package com.kamcci.numberbox.restapi.config.mock

import com.kamcci.numberbox.app.usecase.common.FileUseCase
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class FileMockBeanConfig {
    @Bean
    fun fileUseCase(): FileUseCase = mock()
}