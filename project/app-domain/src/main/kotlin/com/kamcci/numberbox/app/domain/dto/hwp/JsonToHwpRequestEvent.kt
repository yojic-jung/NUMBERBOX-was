package com.kamcci.numberbox.app.domain.dto.hwp

import java.util.UUID

/**
 * json to hwp 변환 요청
 */
data class JsonToHwpRequestEvent(
    val memberId: UUID,
    // json 형식 수식 문자열 : 문자, 수식문법, binary 문자열 이미지 포함
    val jsonContents: String
)