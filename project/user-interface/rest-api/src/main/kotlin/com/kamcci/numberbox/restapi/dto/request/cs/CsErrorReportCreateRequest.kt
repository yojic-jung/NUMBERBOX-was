package com.kamcci.numberbox.restapi.dto.request.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.BrowserType
import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import com.kamcci.numberbox.app.domain.enumeration.cs.OsType
import org.springframework.web.multipart.MultipartFile

/**
 * 고객 센터 신고 문의 생성 request
 */
data class CsErrorReportCreateRequest(
    // 오류 타입
    val errType: CSErrorType,
    // 문제 오류 신고시 문제 id(문제 오류 신고 아니면 Null)
    val contentsId: Long?,
    // 문의 내용
    val reportContents: String,
    // 사용자 os
    val clientOs: OsType,
    // 사용자 브라우저
    val clientBrowser: BrowserType,
    // 참고 이미지1
    val firstImgFile: MultipartFile?,
    // 참고 이미지2
    val secondImgFile: MultipartFile?,
    // 참고 이미지3
    val thirdImgFile: MultipartFile?,
)