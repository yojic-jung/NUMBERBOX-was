package com.kamcci.numberbox.app.domain.dto.math

import java.util.*

/**
 * 수학문제 생성 및 수정 dto
 */
data class MathContentsModifyDto(
    // 제작자
    val memberId: UUID,
    // 단원 id
    val unitId: Int,
    // 유형 id
    val typeId: Int,
    // 수학 문제
    val contents: String,
    // 해설
    val solution: String?,
    // 주관식 정답
    val answer: String?,
    // 객관식 정답
    val choiceAnswer: List<String>?,
    // 객관식 1번
    val firNo: String?,
    // 객관식 2번
    val secNo: String?,
    // 객관식 3번
    val thrNo: String?,
    // 객관식 4번
    val fourNo: String?,
    // 객관식 5번
    val fifNo: String?,
    // 난이도
    val quesLevel: Int,
)