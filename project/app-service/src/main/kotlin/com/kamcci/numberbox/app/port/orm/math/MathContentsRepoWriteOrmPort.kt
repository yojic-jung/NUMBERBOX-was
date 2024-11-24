package com.kamcci.numberbox.app.port.orm.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto

/**
 * 문제 저장소 - 변경
 */
interface MathContentsRepoWriteOrmPort {
    // 저장소에 저장
    fun save(modifyDto: MathContentsRepoModifyDto): Boolean

    // 저장소에서 제거
    fun delete(modifyDto: MathContentsRepoModifyDto): Boolean

}