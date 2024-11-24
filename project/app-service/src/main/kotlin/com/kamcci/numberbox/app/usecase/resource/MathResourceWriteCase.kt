package com.kamcci.numberbox.app.usecase.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import java.util.*

/**
 * 수학 학습 자료 - 변경
 */
interface MathResourceWriteCase {
    /**
     * 등록
     */
    fun create(createDto: MathResourceCreateDto): Long

    /**
     * 수정
     */
    fun update(updateDto: MathResourceUpdateDto)

    /**
     * 삭제
     */
    fun deleteByIdAndMemberId(id: Long, memberId: UUID)
}