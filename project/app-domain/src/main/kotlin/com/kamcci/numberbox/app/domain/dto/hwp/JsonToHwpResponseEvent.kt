package com.kamcci.numberbox.app.domain.dto.hwp

/**
 * json to hwp 변환 응답
 */
data class JsonToHwpResponseEvent(
    // hwpConvertFile.id
    val id: Long,
    // hwp 파일 주소
    val fileName: String,
)
