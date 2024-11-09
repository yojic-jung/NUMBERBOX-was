package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPhoneUpdtDto
import com.kamcci.numberbox.app.port.orm.member.MemberPrivateModifyOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberPrivateEntity.memberPrivateEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class MemberPrivateModifyRepository : MemberPrivateModifyOrmPort, BaseRepository() {
    override fun updatePhoneNumber(phoneUpdtDto: MemberPhoneUpdtDto): Boolean {
        return queryFactory
            .update(memberPrivateEntity)
            .set(memberPrivateEntity.phoneNumber, phoneUpdtDto.phoneNumber)
            .set(memberPrivateEntity.sysUpdateTime, LocalDateTime.now())
            .where(memberPrivateEntity.memberId.eq(phoneUpdtDto.memberId))
            .execute() > 0
    }
}