package com.kamcci.numberbox.app.port.orm.member

import java.util.*

/**
 * 회원 권한 변경
 */
interface MemberRoleWriteOrmPort {
    /**
     * user 권한 사용자 권한 생성
     */
    fun saveUserRole(memberId: UUID): Long

    /**
     * 휴먼 계정 활성/비활성
     */
    fun updateEnabledById(id: UUID, enabled: Boolean): Boolean
}