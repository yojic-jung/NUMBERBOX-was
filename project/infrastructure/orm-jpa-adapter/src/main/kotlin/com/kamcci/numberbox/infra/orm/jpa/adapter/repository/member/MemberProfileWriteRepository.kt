package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberProfileImgUpdtDto
import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.port.orm.member.MemberProfileWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberProfileEntity.memberProfileEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.member.MemberProfileFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberProfileWriteRepository : MemberProfileWriteOrmPort, BaseRepository() {

    override fun save(uuid: UUID, nickName: String): Long {
        val memberProfileEntity = MemberProfileFactory.getSaveEntity(uuid, nickName)
        em.persist(memberProfileEntity)
        return memberProfileEntity.id
    }

    override fun updateProfileTypeByMemberId(memberId: UUID, profileType: ProfileType): Long {
        return queryFactory
            .update(memberProfileEntity)
            .set(memberProfileEntity.profileType, profileType)
            .where(memberProfileEntity.memberId.eq(memberId))
            .execute()
    }

    override fun updateImgByMemberId(updateDto: MemberProfileImgUpdtDto): Long {
        return queryFactory
            .update(memberProfileEntity)
            .set(memberProfileEntity.profileImgPath, updateDto.profileImgPath)
            .set(memberProfileEntity.profileImgName, updateDto.profileImgName)
            .where(memberProfileEntity.memberId.eq(updateDto.memberId))
            .execute()
    }

    override fun updateNicknameByMemberId(memberId: UUID, nickname: String): Long {
        return queryFactory
            .update(memberProfileEntity)
            .set(memberProfileEntity.nickname, nickname)
            .where(memberProfileEntity.memberId.eq(memberId))
            .execute()
    }

    override fun updateHwpDownCntByMemberId(hwpDownCnt: Int): Long {
        return queryFactory
            .update(memberProfileEntity)
            .set(memberProfileEntity.hwpDownCnt, hwpDownCnt)
            .execute()
    }
}