package com.kamcci.numberbox.app.domain.vo.math

/**
 * 수학 유형 정보
 */
data class MathTypeInfoVo(
    // 단원 id
    val unitId: Int,
    // 유형 id
    val typeId: Int,
    // 문제 유형
    val quesType: String,
    // 순서
    val typeOrder: Int,
)