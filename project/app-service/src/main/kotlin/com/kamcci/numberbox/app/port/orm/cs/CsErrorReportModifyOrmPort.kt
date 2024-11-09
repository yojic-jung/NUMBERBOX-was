package com.kamcci.numberbox.app.port.orm.cs

import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportSaveDto

/**
 * 고객 센터 - 문의
 */
interface CsErrorReportModifyOrmPort {
    // 신고 문의 저장
    fun create(saveDto: CsErrorReportSaveDto): Long
}