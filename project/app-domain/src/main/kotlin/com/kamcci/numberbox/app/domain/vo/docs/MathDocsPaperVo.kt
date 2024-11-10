package com.kamcci.numberbox.app.domain.vo.docs

import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import java.time.LocalDateTime

/**
 * 나의 제작 학습지 정보
 */
data class MathDocsPaperVo(
    val id: Long,
    // 수학문제 id list
    val contentsIdList: List<Long>,
    // 학년
    val docsGrade: String?,
    // 학습지 제목
    val docsTitle: String?,
    // 학습지 부제목
    val docsSubTitle: String?,
    // 출제자
    val docsOwner: String?,
    // 문서 타입
    val docsSttsType: DocsStatusType,
    val sysCreateDate: LocalDateTime,
    val sysUpdateDate: LocalDateTime,
)