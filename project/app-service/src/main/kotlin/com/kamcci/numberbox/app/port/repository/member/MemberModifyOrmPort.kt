package com.kamcci.numberbox.app.port.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPasswdUpdtDto
import java.time.LocalDateTime
import java.util.*

interface MemberModifyOrmPort {
    /**
     * 비밀번호 변경
     */
    fun updatePassword(passwordUpdtDto: MemberPasswdUpdtDto): Boolean

    /**
     * 휴대폰 번호 변경
     */
    fun updatePhoneNumber()

    /**
     * 회원 탈퇴
     */
    fun drop(memberId: UUID)

    /**
     * 실패 카운트를 변경함(실패 시간은 현재시간으로 변경)
     */
    fun updateFailCountById(userId: UUID, failCount: Int): Boolean

    /**
     * 실패 시간만 변경함
     */
    fun updateLastFailTimeById(userId: UUID, lastFailTime: LocalDateTime): Boolean
}