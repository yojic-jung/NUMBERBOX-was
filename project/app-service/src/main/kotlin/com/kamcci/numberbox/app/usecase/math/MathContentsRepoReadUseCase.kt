package com.kamcci.numberbox.app.usecase.math

import java.util.*

/**
 * 문제 저장소 - 조회
 */
interface MathContentsRepoReadUseCase {

    fun findContentsIdByMemberId(memberId: UUID): List<Long>

    // 저장소 문제 존재 여부
    fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean
}