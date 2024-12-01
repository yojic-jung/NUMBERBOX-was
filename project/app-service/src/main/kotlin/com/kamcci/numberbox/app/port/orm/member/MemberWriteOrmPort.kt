package com.kamcci.numberbox.app.port.orm.member

import java.time.LocalDateTime
import java.util.*

/**
 * 회원 변경
 */
interface MemberWriteOrmPort {

    /**
     * 회원 등록
     */
    fun save(email: String, password: String): UUID

    /**
     * 회원 탈퇴
     */
    fun drop(memberId: UUID): Long

    /**
     * 비밀번호 변경
     */
    fun updatePassword(memberId: UUID, password: String): Long

    /**
     * 비밀번호 변경
     */
    fun updatePassword(memberId: List<UUID>, password: String?): Long

    /**
     * 비밀번호 변경
     */
    fun updatePassword(email: String, password: String): Long

    /**
     * 실패 카운트를 변경함(실패 시간은 현재시간으로 변경)
     */
    fun updateFailCountById(userId: UUID, failCount: Int): Long

    /**
     * 실패 시간만 변경함
     */
    fun updateLastFailTimeById(userId: UUID, lastFailTime: LocalDateTime): Long
}