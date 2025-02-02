package com.kamcci.numberbox.restapi.config.stub

import com.kamcci.numberbox.app.service.stub.usecase.cs.MockCsErrorReportReadCase
import com.kamcci.numberbox.app.service.stub.usecase.cs.MockCsErrorReportWriteCase
import com.kamcci.numberbox.app.usecase.cs.CsErrorReportReadCase
import com.kamcci.numberbox.app.usecase.cs.CsErrorReportWriteCase
import org.springframework.context.annotation.Bean

class CsMockBeanConfig {
    @Bean
    fun csErrorReportReadCase(): CsErrorReportReadCase = MockCsErrorReportReadCase()

    @Bean
    fun csErrorReportWriteCase(): CsErrorReportWriteCase = MockCsErrorReportWriteCase()
}