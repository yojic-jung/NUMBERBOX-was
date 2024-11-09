package com.kamcci.numberbox.app.domain.dto.docs

import java.util.*

/**
 * 수학 학습지 제작 사용 기록
 */
data class MathDocsUsageCreateDto(
    val contentsIdList: String,
    // 학습지 제작자 id
    val memberId: UUID,
    // 학년
    val docsGrade: String,
    // 학습지 제목
    val docsTitle: String,
    // 학습지 부제목
    val docsSubTitle: String,
    // 출제자
    val docsOwner: String,
)