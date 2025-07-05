package com.kamcci.numberbox.app.domain.dto.hwp

/**
 * json to hwp 변환 요청
 */
data class JsonToHwpRequestEvent(
    // hwpConvertFile.id
    val id: Long,
    // json 형식 파일 : 문자, 수식문법, binary 문자열 이미지 포함
    val fileName: String,
)
