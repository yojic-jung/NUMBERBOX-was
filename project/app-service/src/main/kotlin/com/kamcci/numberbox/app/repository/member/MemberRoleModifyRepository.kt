package com.kamcci.numberbox.app.repository.member

import java.util.*

interface MemberRoleModifyRepository {
    fun updateEnabledById(id: UUID, enabled: Boolean): Boolean
}