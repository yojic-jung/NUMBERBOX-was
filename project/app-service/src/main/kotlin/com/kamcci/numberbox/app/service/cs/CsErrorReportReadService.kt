package com.kamcci.numberbox.app.service.cs

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.cs.CsErrReportVo
import com.kamcci.numberbox.app.port.orm.cs.CsErrorReportReadOrmPort
import com.kamcci.numberbox.app.usecase.cs.CsErrorReportReadCase
import java.util.*

@UseCase
class CsErrorReportReadService(
    private val csErrorReportReadOrmPort: CsErrorReportReadOrmPort
) : CsErrorReportReadCase {
    override fun readByMemberId(memberId: UUID): List<CsErrReportVo> = csErrorReportReadOrmPort.readByMemberId(memberId)
}