package com.kamcci.numberbox.app.repository.member

interface MemberRoleModifyRepository {
    fun updateEnabledById(userId: Long, enabled: Boolean): Boolean


}