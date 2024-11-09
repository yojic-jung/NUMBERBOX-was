package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.port.orm.member.MemberRoleSaveOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.MemberEntity
import com.kamcci.numberbox.infra.orm.factory.member.MemberRoleFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberRoleSaveOrmAdapter : MemberRoleSaveOrmPort, BaseRepository() {
    override fun saveUserRole(memberId: UUID): Long {
        val memberEntity = em.find(MemberEntity::class.java, memberId)
        val memberRole = MemberRoleFactory.getUserRoleEntity(memberEntity)
        em.persist(memberRole)
        return memberRole.id
    }
}