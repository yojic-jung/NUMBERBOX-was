package com.kamcci.numberbox.app.domain.dto.docs

import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType

/**
 * 학습지 생성 dto
 */
data class MathDocsPaperCreateDto(
    // 수학문제 id
    val contentsIdList: List<Long>,
    // 학년
    val docsGrade: String,
    // 학습지 제목
    val docsTitle: String,
    // 학습지 부제목
    val docsSubTitle: String,
    // 출제자
    val docsOwner: String,
    // 학습지 에러 타입
    val docsStts: DocsStatusType,
)