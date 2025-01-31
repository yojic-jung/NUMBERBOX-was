package com.kamcci.numberbox.app.service.stub.usecase.cs

import com.kamcci.numberbox.app.domain.vo.cs.CsErrReportVo
import com.kamcci.numberbox.app.usecase.cs.CsErrorReportReadCase
import java.util.*

class MockCsErrorReportReadCase : CsErrorReportReadCase {
    override fun readByMemberId(memberId: UUID): List<CsErrReportVo> {
        TODO("Not yet implemented")
    }
}