package com.kamcci.numberbox.app.port.repository.member

import java.util.*

/**
 * 회원 생성
 */
interface MemberSaveOrmPort {
    fun save(email: String, password: String): UUID
}