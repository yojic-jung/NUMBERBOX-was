package com.kamcci.numberbox.infra.orm.repository.member

import com.kamcci.numberbox.app.repository.member.MemberRoleSaveRepository
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.factory.member.MemberRoleFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberRoleSaveRepositoryImpl : MemberRoleSaveRepository, BaseRepository() {
    override fun saveUserRole(uuid: UUID) {
        val memberRole = MemberRoleFactory.getUserRoleEntity(uuid)
        em.persist(memberRole)
    }
}