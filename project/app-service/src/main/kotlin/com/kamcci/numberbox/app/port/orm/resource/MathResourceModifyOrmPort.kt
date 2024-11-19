package com.kamcci.numberbox.app.port.orm.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateOrmDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdtOrmDto
import java.util.*

/**
 * 학습 자료 영속화
 */
interface MathResourceModifyOrmPort {
    // 등록
    fun create(createDto: MathResourceCreateOrmDto): Long

    // 수정
    fun update(updateDto: MathResourceUpdtOrmDto)

    // 삭제
    fun deleteByIdAndMemberId(id: Long, memberId: UUID): Long
}