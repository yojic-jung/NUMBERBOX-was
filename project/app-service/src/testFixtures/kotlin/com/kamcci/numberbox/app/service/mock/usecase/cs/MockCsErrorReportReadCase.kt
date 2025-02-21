package com.kamcci.numberbox.app.service.mock.usecase.cs

import com.kamcci.numberbox.app.domain.vo.cs.CsErrReportVo
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXCEPTION_MEMBER_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.STUB_EXCEPTION_MSG
import com.kamcci.numberbox.app.service.sample.CsSampleData.getCsErrReportVoList
import com.kamcci.numberbox.app.usecase.cs.CsErrorReportReadCase
import java.util.*

class MockCsErrorReportReadCase : CsErrorReportReadCase {
    override fun readByMemberId(memberId: UUID): List<CsErrReportVo> {
        if (memberId == EXCEPTION_MEMBER_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
        return getCsErrReportVoList()
    }
}