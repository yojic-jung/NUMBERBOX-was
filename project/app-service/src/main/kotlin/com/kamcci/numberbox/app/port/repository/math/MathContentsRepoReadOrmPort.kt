package com.kamcci.numberbox.app.port.repository.math

import java.util.*

/**
 * 문제 저장소 - 변경
 */
interface MathContentsRepoReadOrmPort {
    // 내 저장소 문제 id 목록 조회
    fun readContentsIdByMemberId(memberId: UUID): List<Long>

    // 존재여부 파악
    fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean
}