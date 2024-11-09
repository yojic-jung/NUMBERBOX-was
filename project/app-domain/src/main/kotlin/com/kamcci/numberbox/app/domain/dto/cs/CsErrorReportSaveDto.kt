package com.kamcci.numberbox.app.domain.dto.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.BrowserType
import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import com.kamcci.numberbox.app.domain.enumeration.cs.OsType
import com.kamcci.numberbox.app.domain.enumeration.cs.ReportSttsType
import java.util.*

/**
 * 고객 센터 신고 문의 생성 dto
 */
data class CsErrorReportSaveDto(
    // 오류 타입
    val errType: CSErrorType,
    // 문제 오류 신고시 문제 id(문제 오류 신고 아니면 Null)
    val contentsId: Long?,
    // 문의자
    val reportMemberId: UUID,
    // 문의 내용
    val reportContents: String,
    // 사용자 os
    val clientOs: OsType,
    // 사용자 브라우저
    val clientBrowser: BrowserType,
    // 참고 이미지1 - 경로
    val firstImgPath: String?,
    // 참고 이미지1 - 이름
    val firstImgName: String?,
    // 참고 이미지2 - 이름
    val secondImgPath: String?,
    // 참고 이미지2 - 경로
    val secondImgName: String?,
    // 참고 이미지3- 경로
    val thirdImgPath: String?,
    // 참고 이미지3 - 이름
    val thirdImgName: String?,
    // 접수 상태
    val reportStts: ReportSttsType
)