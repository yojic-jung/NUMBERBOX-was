package com.kamcci.numberbox.restapi.dto.request.hwp

/**
 * 수정한 html 파일 문자열
 */
data class HwpToHtmlUpdateRequest(
    val id: Long,
    // 변환 컨텐츠
    val contents: String,
)