package com.kamcci.numberbox.app.service.stub.port.orm.cs

import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportCreateDto
import com.kamcci.numberbox.app.port.orm.cs.CsErrorReportWriteOrmPort

class MockCsErrorReportWriteOrmPort : CsErrorReportWriteOrmPort {
    override fun create(createDto: CsErrorReportCreateDto): Long {
        return 1L
    }
}