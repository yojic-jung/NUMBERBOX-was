package com.kamcci.numberbox.app.service.mock.usecase.cs

import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportCreateDto
import com.kamcci.numberbox.app.usecase.cs.CsErrorReportWriteCase

class MockCsErrorReportWriteCase : CsErrorReportWriteCase {
    override fun createReport(createDto: CsErrorReportCreateDto): Long {
        return 1L
    }
}