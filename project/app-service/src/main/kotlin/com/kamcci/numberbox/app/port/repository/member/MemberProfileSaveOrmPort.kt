package com.kamcci.numberbox.app.port.repository.member

import java.util.*

/**
 * 회원 프로필 생성
 */
interface MemberProfileSaveOrmPort {
    fun save(uuid: UUID, nickName: String): Long
}