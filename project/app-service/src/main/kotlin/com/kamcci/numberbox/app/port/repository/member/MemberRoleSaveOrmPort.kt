package com.kamcci.numberbox.app.port.repository.member

import java.util.*

interface MemberRoleSaveOrmPort {
    /**
     * user 권한 사용자 권한 생성
     */
    fun saveUserRole(memberId: UUID): Long
}