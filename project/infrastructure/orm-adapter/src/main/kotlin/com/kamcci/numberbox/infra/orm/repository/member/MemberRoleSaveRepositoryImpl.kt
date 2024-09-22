package com.kamcci.numberbox.infra.orm.repository.member

import com.kamcci.numberbox.app.repository.member.MemberRoleSaveRepository
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.MemberEntity
import com.kamcci.numberbox.infra.orm.factory.member.MemberRoleFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberRoleSaveRepositoryImpl : MemberRoleSaveRepository, BaseRepository() {
    override fun saveUserRole(memberId: UUID): Long {
        val memberEntity = em.find(MemberEntity::class.java, memberId)
        val memberRole = MemberRoleFactory.getUserRoleEntity(memberEntity)
        em.persist(memberRole)
        return memberRole.id
    }
}