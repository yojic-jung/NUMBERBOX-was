package com.kamcci.numberbox.app.domain.dto.docs

/**
 * 수학 학습지 제작 사용 기록
 */
data class MathDocsUsageUpdtDto(
    val id: Long,
    // 수학 문제 id
    val contentsIdList: List<Long>,
    // 학년
    val docsGrade: String,
    // 학습지 제목
    val docsTitle: String,
    // 학습지 부제목
    val docsSubTitle: String,
    // 출제자
    val docsOwner: String,
)