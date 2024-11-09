package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.port.orm.member.MemberSaveOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.MemberEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberSaveOrmAdapter : MemberSaveOrmPort, BaseRepository() {
    override fun save(email: String, password: String): UUID {
        val memberEntity = MemberEntity().apply {
            this.email = email
            this.password = password
        }
        em.persist(memberEntity)
        return memberEntity.id!!
    }
}