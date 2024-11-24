package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPhoneUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import com.kamcci.numberbox.app.port.orm.member.MemberPrivateWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberPrivateEntity.memberPrivateEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.member.MemberPrivateFactory
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class MemberPrivateWriteRepository : MemberPrivateWriteOrmPort, BaseRepository() {
    override fun save(memberId: UUID, privateSignUpDto: MemberPrivateSignUpDto): UUID {
        val memberPrivateEntity = MemberPrivateFactory.getSaveEntity(memberId, privateSignUpDto)
        em.persist(memberPrivateEntity)
        return memberPrivateEntity.memberId!!
    }

    override fun updatePhoneNumber(phoneUpdtDto: MemberPhoneUpdtDto): Boolean {
        return queryFactory
            .update(memberPrivateEntity)
            .set(memberPrivateEntity.phoneNumber, phoneUpdtDto.phoneNumber)
            .set(memberPrivateEntity.sysUpdateTime, LocalDateTime.now())
            .where(memberPrivateEntity.memberId.eq(phoneUpdtDto.memberId))
            .execute() > 0
    }

    override fun updatePrivateInfoToNull(memberId: UUID): Long {
        return queryFactory
            .update(memberPrivateEntity)
            .set(memberPrivateEntity.userName, null as String?)
            .set(memberPrivateEntity.phoneNumber, null as String?)
            .set(memberPrivateEntity.birth, null as String?)
            .set(memberPrivateEntity.sysUpdateTime, LocalDateTime.now())
            .where(memberPrivateEntity.memberId.eq(memberId))
            .execute()
    }
}