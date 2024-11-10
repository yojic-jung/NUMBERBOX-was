package com.kamcci.numberbox.app.port.orm.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperUpdtDto
import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import java.util.*

/**
 * 학습지 정보 변경
 */
interface MathDocsPaperModifyOrmPort {
    // 학습지 정보 생성
    fun create(memberId: UUID, createDto: MathDocsPaperCreateDto): Long

    // 학습지 정보 수정
    fun update(memberId: UUID, updtDto: MathDocsPaperUpdtDto): Long

    // 학습지 상태 변경
    fun updateDocsSttsByIdAndMemberId(docsId: Long, memberId: UUID, docsStts: DocsStatusType): Long

    // 학습지 삭제
    fun delete(docsId: Long, memberId: UUID): Long
}