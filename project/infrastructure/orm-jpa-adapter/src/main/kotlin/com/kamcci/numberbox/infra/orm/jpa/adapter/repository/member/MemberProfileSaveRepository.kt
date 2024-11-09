package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.port.orm.member.MemberProfileSaveOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.member.MemberProfileFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberProfileSaveRepository : MemberProfileSaveOrmPort, BaseRepository() {
    override fun save(uuid: UUID, nickName: String): Long {
        val memberProfileEntity = MemberProfileFactory.getSaveEntity(uuid, nickName)
        em.persist(memberProfileEntity)
        return memberProfileEntity.id
    }
}