package com.kamcci.numberbox.restapi.config.mock

import com.kamcci.modules.auth.control.service.TokenResponseService
import org.mockito.Mockito.mock
import org.springframework.context.annotation.Bean

class TokenMockBeanConfig {
    @Bean
    fun tokenResponseService(): TokenResponseService = mock()
}