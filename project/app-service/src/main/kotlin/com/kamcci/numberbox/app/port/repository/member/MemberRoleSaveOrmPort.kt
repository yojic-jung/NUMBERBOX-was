package com.kamcci.numberbox.app.port.repository.member

import java.util.*

/**
 * 회원 권한 변경
 */
interface MemberRoleSaveOrmPort {
    /**
     * user 권한 사용자 권한 생성
     */
    fun saveUserRole(memberId: UUID): Long
}