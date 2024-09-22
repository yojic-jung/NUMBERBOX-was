package com.kamcci.numberbox.app.port.repository.member

import java.util.*

interface MemberRoleModifyOrmPort {
    fun updateEnabledById(id: UUID, enabled: Boolean): Boolean
}