package com.kamcci.numberbox.infra.orm.repository.member

import com.kamcci.numberbox.app.repository.member.MemberProfileSaveRepository
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.factory.member.MemberProfileFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberProfileSaveRepositoryImpl : MemberProfileSaveRepository, BaseRepository() {
    override fun save(uuid: UUID, nickName: String): Long {
        val memberProfileEntity = MemberProfileFactory.getSaveEntity(uuid, nickName)
        em.persist(memberProfileEntity)
        return memberProfileEntity.id
    }
}