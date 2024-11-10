package com.kamcci.numberbox.app.usecase.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperUpdtDto
import java.util.*

/**
 * 학습지 정보 변경
 */
interface MathDocsPaperModifyUseCase {
    // 학습지 정보 생성
    fun create(memberId: UUID, createDto: MathDocsPaperCreateDto): Long

    // 학습지 정보 수정
    fun update(memberId: UUID, updtDto: MathDocsPaperUpdtDto)

    // 학습지 삭제
    fun delete(docsId: Long, memberId: UUID)
}