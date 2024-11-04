package com.kamcci.numberbox.app.usecase.math

import java.util.*

/**
 * 문제 저장소 - 조회
 */
interface MathContentsRepoReadUseCase {

    // 멤버 id로 컨텐츠 id 조회
    fun readContentsIdByMemberId(memberId: UUID): List<Long>

    // 저장소 문제 존재 여부
    fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean
}