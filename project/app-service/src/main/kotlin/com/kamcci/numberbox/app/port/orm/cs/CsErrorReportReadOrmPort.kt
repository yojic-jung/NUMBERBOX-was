package com.kamcci.numberbox.app.port.orm.cs

import com.kamcci.numberbox.app.domain.vo.cs.CsErrReportVo
import java.util.*

/**
 * 고객 센터 - 문의
 */
interface CsErrorReportReadOrmPort {
    // 신고 문의 내역
    fun readByMemberId(memberId: UUID): List<CsErrReportVo>
}