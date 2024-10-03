package com.kamcci.numberbox.app.port.repository.member

import java.time.LocalDateTime
import java.util.*

interface MemberReadOrmPort {

    // 회원 id 조회
    fun findIdByEmail(email: String): UUID?

    // 이메일 조회
    fun findEmailByUsernameAndPhone(userName: String, phoneNumber: String): String?

    // 로그인 실패 카운트 조회
    fun findFailCountById(id: UUID): Int?

    // 마지막 로그인 실패 시간 조회
    fun findLastFailTimeById(id: UUID): LocalDateTime?

    // 이메일 존재여부 조회
    fun existsByEmail(email: String): Boolean
}