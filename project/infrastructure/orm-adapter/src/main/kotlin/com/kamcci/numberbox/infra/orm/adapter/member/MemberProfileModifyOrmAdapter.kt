package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.domain.dto.member.MemberProfileImgUpdtDto
import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.port.orm.member.MemberProfileModifyOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.QMemberProfileEntity.memberProfileEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberProfileModifyOrmAdapter : MemberProfileModifyOrmPort, BaseRepository() {

    override fun updateProfileTypeByMemberId(memberId: UUID, profileType: ProfileType): Long {
        return queryFactory
            .update(memberProfileEntity)
            .set(memberProfileEntity.profileType, profileType)
            .where(memberProfileEntity.memberId.eq(memberId))
            .execute()
    }

    override fun updateImgByMemberId(profileImgUpdtDto: MemberProfileImgUpdtDto): Long {
        return queryFactory
            .update(memberProfileEntity)
            .set(memberProfileEntity.profileImgPath, profileImgUpdtDto.profileImgPath)
            .set(memberProfileEntity.profileImgName, profileImgUpdtDto.profileImgName)
            .where(memberProfileEntity.memberId.eq(profileImgUpdtDto.memberId))
            .execute()
    }

    override fun updateNicknameByMemberId(memberId: UUID, nickname: String): Long {
        return queryFactory
            .update(memberProfileEntity)
            .set(memberProfileEntity.nickname, nickname)
            .where(memberProfileEntity.memberId.eq(memberId))
            .execute()
    }

}