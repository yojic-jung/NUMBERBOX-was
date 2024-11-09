package com.kamcci.numberbox.app.port.orm.member

import java.util.*

/**
 * 회원 권한 변경
 */
interface MemberRoleModifyOrmPort {
    fun updateEnabledById(id: UUID, enabled: Boolean): Boolean
}