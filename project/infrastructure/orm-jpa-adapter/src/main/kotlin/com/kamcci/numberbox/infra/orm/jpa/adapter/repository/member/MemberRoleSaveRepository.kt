package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.port.orm.member.MemberRoleSaveOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.member.MemberRoleFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberRoleSaveRepository : MemberRoleSaveOrmPort, BaseRepository() {
    override fun saveUserRole(memberId: UUID): Long {
        val memberEntity = em.find(MemberEntity::class.java, memberId)
        val memberRole = MemberRoleFactory.getUserRoleEntity(memberEntity)
        em.persist(memberRole)
        return memberRole.id
    }
}