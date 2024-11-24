package com.kamcci.numberbox.app.port.orm.member

import java.time.LocalDateTime
import java.util.*

/**
 * 회원 조회
 */
interface MemberReadOrmPort {

    // 회원 id 조회
    fun readIdByEmail(email: String): UUID?

    // 이메일 조회
    fun readEmailByUsernameAndPhone(userName: String, phoneNumber: String): String?

    // 이메일 존재 여부
    fun existEmail(email: String): Boolean

    // 비밀번호 조회
    fun readPasswordByMemberId(memberId: UUID): String?

    // 로그인 실패 카운트 조회
    fun readFailCountById(id: UUID): Int?

    // 마지막 로그인 실패 시간 조회
    fun readLastFailTimeById(id: UUID): LocalDateTime?

    // 이메일 존재여부 조회
    fun existsByEmail(email: String): Boolean

    /**
     * 임시 비밀번호 발급자 id 조회
     */
    fun readByIsTmpPassword(isTrue: Boolean, limit: Long): List<UUID>

    // 계정 활성 여부로 조회(관리자 제외)
    fun readUserIdByHumanStatus(humanStatus: Int): List<UUID>
}