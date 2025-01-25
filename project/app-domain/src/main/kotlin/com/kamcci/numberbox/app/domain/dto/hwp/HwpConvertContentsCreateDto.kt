package com.kamcci.numberbox.app.domain.dto.hwp

import java.util.*

/**
 * hwp to html 변환 컨텐츠 등록용
 */
data class HwpConvertContentsCreateDto(
    val memberId: UUID,
    // 파일 변환 완료 여부
    val isConverted: Boolean = true,
    // 원본파일명
    val fileName: String,
    // xhtml 컨텐츠 내용 - html 스크립트 문법 문자열
    val contents: String,
    // xhtml 내부 이미지 파일 경로(파일 이름 제외 이미지 경로만)
    val imgPath: String,
)