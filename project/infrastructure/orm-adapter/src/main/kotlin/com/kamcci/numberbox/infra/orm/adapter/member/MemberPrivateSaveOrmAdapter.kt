package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import com.kamcci.numberbox.app.port.repository.member.MemberPrivateSaveOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.factory.member.MemberPrivateFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberPrivateSaveOrmAdapter : MemberPrivateSaveOrmPort, BaseRepository() {
    override fun save(memberId: UUID, privateSignUpDto: MemberPrivateSignUpDto): UUID {
        val memberPrivateEntity = MemberPrivateFactory.getSaveEntity(memberId, privateSignUpDto)
        em.persist(memberPrivateEntity)
        return memberPrivateEntity.memberId!!
    }
}