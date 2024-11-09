package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import com.kamcci.numberbox.app.port.orm.member.MemberPrivateSaveOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.member.MemberPrivateFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberPrivateSaveRepository : MemberPrivateSaveOrmPort, BaseRepository() {
    override fun save(memberId: UUID, privateSignUpDto: MemberPrivateSignUpDto): UUID {
        val memberPrivateEntity = MemberPrivateFactory.getSaveEntity(memberId, privateSignUpDto)
        em.persist(memberPrivateEntity)
        return memberPrivateEntity.memberId!!
    }
}