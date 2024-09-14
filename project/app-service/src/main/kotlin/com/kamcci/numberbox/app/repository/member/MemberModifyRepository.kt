package com.kamcci.numberbox.app.repository.member

import java.time.LocalDateTime

interface MemberModifyRepository {
    /**
     * 실패 카운트를 변경함(실패 시간은 현재시간으로 변경)
     */
    fun updateFailCountById(userId: Long, failCount: Int): Boolean

    /**
     * 실패 시간만 변경함
     */
    fun updateLastFailTimeById(userId: Long, lastFailTime: LocalDateTime): Boolean
}