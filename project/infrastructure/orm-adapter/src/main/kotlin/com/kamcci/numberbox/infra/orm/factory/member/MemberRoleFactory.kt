package com.kamcci.numberbox.infra.orm.factory.member

import com.kamcci.numberbox.infra.orm.entity.member.MemberRoleEntity
import java.util.*

object MemberRoleFactory {
    fun getUserRoleEntity(uuid: UUID) =
        MemberRoleEntity().apply {
            member.id = uuid
            roleName = "USER"
        }
}