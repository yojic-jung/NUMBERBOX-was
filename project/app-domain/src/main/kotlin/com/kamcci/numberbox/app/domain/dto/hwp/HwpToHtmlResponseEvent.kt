package com.kamcci.numberbox.app.domain.dto.hwp

/**
 * hwp to html 변환 응답
 */
data class HwpToHtmlResponseEvent(
    // hwpConvertFile.id
    val id: Long,
    // html 파일 주소
    val fileName: String
)