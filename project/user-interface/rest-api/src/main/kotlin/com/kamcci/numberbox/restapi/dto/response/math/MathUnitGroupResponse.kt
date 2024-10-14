package com.kamcci.numberbox.restapi.dto.response.math

/**
 * 단원 정보 상하위별 구분
 * - 학년 -> 대단원
 * - 대단원 -> 중단원
 * - 중단원 -> 소단원
 */
data class MathUnitGroupResponse(
    // 단원 고유번호
    val unitUniqNo: Int,
    // 부모 단원
    val parentVal: String,
    // 자기 자신 단원
    val mainVal: String
)