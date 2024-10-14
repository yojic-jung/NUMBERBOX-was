package com.kamcci.numberbox.app.domain.vo.math

/**
 * 수학 단원 정보
 */
data class MathUnitInfoVo(
    val id: Int,
    // 학년
    val subject: String,
    // 대단원
    val firUnit: String,
    // 중단원
    val secUnit: String,
    // 소단원
    val thrUnit: String,
)