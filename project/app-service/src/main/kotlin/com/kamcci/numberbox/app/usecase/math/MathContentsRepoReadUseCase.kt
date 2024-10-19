package com.kamcci.numberbox.app.usecase.math

import java.util.*

/**
 * 문제 저장소 - 조회
 */
interface MathContentsRepoReadUseCase {
    // 내 저장소 문제 id 목록 조회
    fun findContentsIdByMemberId(memberId: UUID): List<Long>
}