package com.kamcci.numberbox.restapi.config.mock

import com.kamcci.numberbox.app.usecase.cs.CsErrorReportReadCase
import com.kamcci.numberbox.app.usecase.cs.CsErrorReportWriteCase
import org.mockito.Mockito.mock
import org.springframework.context.annotation.Bean

class CsMockBeanConfig {
    @Bean
    fun csErrorReportReadCase(): CsErrorReportReadCase = mock()

    @Bean
    fun csErrorReportWriteCase(): CsErrorReportWriteCase = mock()
}