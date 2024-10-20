package com.kamcci.numberbox.app.domain.dto.math

import com.kamcci.numberbox.app.domain.enumeration.math.MathTypeClassifyType

/**
 * 유사문제 출처 정보 생성 dto
 */
data class MathConSimilarSrcCreateDto(
    // 출처 - 교재
    val orgSrcRef: String,
    // 출처 - 문제 번호
    val orgSrcNo: Int,
    // 출처 - 페이지 번호
    val orgSrcPage: Int,
    // 쇄 연도
    val copyrightYear: String,
    // 문제 유형
    val mathTypeClassify: MathTypeClassifyType,
)