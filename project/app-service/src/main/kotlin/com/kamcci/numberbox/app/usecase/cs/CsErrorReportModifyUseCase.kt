package com.kamcci.numberbox.app.usecase.cs

import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportCreateDto

/**
 * 고객 센터 - 문의
 */
interface CsErrorReportModifyUseCase {
    // 신고하기
    fun createReport(createDto: CsErrorReportCreateDto): Long
}