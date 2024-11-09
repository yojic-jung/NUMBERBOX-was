package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.port.orm.member.MemberSaveOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberSaveRepository : MemberSaveOrmPort, BaseRepository() {
    override fun save(email: String, password: String): UUID {
        val memberEntity = MemberEntity().apply {
            this.email = email
            this.password = password
        }
        em.persist(memberEntity)
        return memberEntity.id!!
    }
}