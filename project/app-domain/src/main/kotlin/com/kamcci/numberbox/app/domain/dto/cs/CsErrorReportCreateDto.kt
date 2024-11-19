package com.kamcci.numberbox.app.domain.dto.cs

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.domain.enumeration.cs.BrowserType
import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import com.kamcci.numberbox.app.domain.enumeration.cs.OsType
import java.util.*

/**
 * 고객 센터 신고 문의 생성 dto
 */
data class CsErrorReportCreateDto(
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
    // 참고 이미지1
    val firstImgFile: FileUploadDto?,
    // 참고 이미지2
    val secondImgFile: FileUploadDto?,
    // 참고 이미지3
    val thirdImgFile: FileUploadDto?,
)