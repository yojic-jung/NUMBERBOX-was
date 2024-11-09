package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPhoneUpdtDto
import com.kamcci.numberbox.app.port.orm.member.MemberPrivateModifyOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.QMemberPrivateEntity.memberPrivateEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class MemberPrivateModifyOrmAdapter : MemberPrivateModifyOrmPort, BaseRepository() {
    override fun updatePhoneNumber(phoneUpdtDto: MemberPhoneUpdtDto): Boolean {
        return queryFactory
            .update(memberPrivateEntity)
            .set(memberPrivateEntity.phoneNumber, phoneUpdtDto.phoneNumber)
            .set(memberPrivateEntity.sysUpdateTime, LocalDateTime.now())
            .where(memberPrivateEntity.memberId.eq(phoneUpdtDto.memberId))
            .execute() > 0
    }
}