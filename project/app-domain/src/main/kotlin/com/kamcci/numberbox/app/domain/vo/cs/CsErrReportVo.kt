package com.kamcci.numberbox.app.domain.vo.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import com.kamcci.numberbox.app.domain.enumeration.cs.ReportSttsType
import java.time.LocalDateTime

/**
 * 고객센터 문의 내용
 */
data class CsErrReportVo(
    val id: Long,
    // 오류 타입
    val errType: CSErrorType,
    val contentsId: Long,
    // 문의 내용
    val reportContents: String?,
    // 답변 내용
    val replyContents: String?,
    val firstImgPath: String?,
    val firstImgName: String?,
    val secondImgPath: String?,
    val secondImgName: String?,
    val thirdImgPath: String?,
    val thirdImgName: String?,
    // 접수 상태
    val reportStts: ReportSttsType,
    val sysCreateDate: LocalDateTime,
    val sysUpdateDate: LocalDateTime,
)