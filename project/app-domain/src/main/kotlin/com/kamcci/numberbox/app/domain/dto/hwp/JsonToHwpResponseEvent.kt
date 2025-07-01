package com.kamcci.numberbox.app.domain.dto.hwp

import java.util.UUID

/**
 * json to hwp 변환 응답
 */
data class JsonToHwpResponseEvent(
    val memberId: UUID,
    // hwp 파일 주소
    val filName: String
)