package com.kamcci.numberbox.app.domain.dto.docs

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType


/**
 * 수학문제 학습지 - 추가 문제 조회 검색 조건
 */
data class MathDocsAdditionalReadDto(
    // 단원 id
    val unitId: Int,
    // 유형 id
    val typeId: Int,
    // 컨텐츠 분류
    val contentsClassifyType: ContentsClassifyType,
)
