package com.kamcci.numberbox.app.domain.dto.hwp

import java.util.UUID

/**
 * hwp to html 변환 요청
 */
data class HwpToHtmlRequestEvent(
    // hwpConvertFile.id
    val id: Long,
    // S3 파일 주소
    val fileName: String
)