package com.kamcci.numberbox.infra.orm.factory.member

import com.kamcci.numberbox.infra.orm.entity.member.MemberEntity
import com.kamcci.numberbox.infra.orm.entity.member.MemberRoleEntity

object MemberRoleFactory {
    fun getUserRoleEntity(memberEntity: MemberEntity): MemberRoleEntity {
        MemberEntity()
        return MemberRoleEntity().apply {
            member = memberEntity
            roleName = "USER"
        }
    }

}