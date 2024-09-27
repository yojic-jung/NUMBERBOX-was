package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.port.repository.member.MemberProfileModifyOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.QMemberProfileEntity.memberProfileEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberProfileModifyOrmAdapter : MemberProfileModifyOrmPort, BaseRepository() {

    override fun modifyProfileTypeByMemberId(memberId: UUID, profileType: ProfileType) {
        queryFactory
            .update(memberProfileEntity)
            .set(memberProfileEntity.profileType, profileType)
            .where(memberProfileEntity.memberId.eq(memberId))
            .execute()
    }

    override fun modifyImgByMemberId() {
    }

    override fun modifyNicknameByMemberId() {
        TODO("Not yet implemented")
    }

}