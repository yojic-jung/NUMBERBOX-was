package com.kamcci.numberbox.app.domain.dto.hwp

import java.util.*

/**
 * 수정한 html 파일 문자열
 */
data class HwpConvertContentsUpdateDto(
    val id: Long,
    // 문제 소유자
    val memberId: UUID,
    // 변환 컨텐츠
    val contents: String,
    // 클라이언트단 문법 정상 변환 여부
    val isGrammarConverted: Boolean
)