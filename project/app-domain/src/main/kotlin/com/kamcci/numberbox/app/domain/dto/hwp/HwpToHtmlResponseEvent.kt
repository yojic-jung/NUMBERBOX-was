package com.kamcci.numberbox.app.domain.dto.hwp

import java.util.UUID

/**
 * hwp to html 변환 응답
 */
data class HwpToHtmlResponseEvent(
    val memberId: UUID,
    // html 파일 주소
    val fileName: String
)