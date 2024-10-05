package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.domain.vo.member.MemberProfileImgVo
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import com.kamcci.numberbox.app.port.repository.member.MemberProfileReadOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.QMemberEntity
import com.kamcci.numberbox.infra.orm.entity.member.QMemberProfileEntity.memberProfileEntity
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberProfileReadOrmAdapter : MemberProfileReadOrmPort, BaseRepository() {
    override fun findByMemberId(memberId: UUID): MemberProfileVo? {
        return queryFactory
            .select(
                Projections.constructor(
                    MemberProfileVo::class.java,
                    memberProfileEntity.id,
                    Expressions.constant(memberId),
                    memberProfileEntity.profileImgName,
                    memberProfileEntity.profileImgPath,
                    memberProfileEntity.profileType,
                )
            )
            .from(memberProfileEntity)
            .where(memberProfileEntity.memberId.eq(memberId))
            .fetchOne()
    }

    override fun findByProfileId(profileId: Long): MemberProfileVo? {
        return queryFactory
            .select(
                Projections.constructor(
                    MemberProfileVo::class.java,
                    memberProfileEntity.id,
                    Expressions.constant(profileId),
                    memberProfileEntity.profileImgName,
                    memberProfileEntity.profileImgPath,
                    memberProfileEntity.profileType,
                )
            )
            .from(QMemberEntity.memberEntity)
            .where(memberProfileEntity.id.eq(profileId))
            .fetchOne()
    }

    override fun findProfileIdByMemberId(memberId: UUID): Long? {
        return queryFactory
            .select(memberProfileEntity.id)
            .from(memberProfileEntity)
            .where(memberProfileEntity.memberId.eq(memberId))
            .fetchOne()
    }

    override fun findProfileImgByMemberId(memberId: UUID): MemberProfileImgVo? {
        return queryFactory
            .select(
                Projections.constructor(
                    MemberProfileImgVo::class.java,
                    memberProfileEntity.id,
                    Expressions.constant(memberId),
                    memberProfileEntity.profileImgPath,
                    memberProfileEntity.profileImgName,
                )
            )
            .from(memberProfileEntity)
            .where(memberProfileEntity.memberId.eq(memberId))
            .fetchOne()
    }

    override fun findByProfileIdList(profileId: List<Long>): List<MemberProfileVo> {
        return queryFactory
            .select(
                Projections.constructor(
                    MemberProfileVo::class.java,
                    memberProfileEntity.id,
                    memberProfileEntity.memberId,
                    memberProfileEntity.profileImgName,
                    memberProfileEntity.profileImgPath,
                    memberProfileEntity.profileType,
                )
            )
            .from(memberProfileEntity)
            .where(memberProfileEntity.id.`in`(profileId))
            .fetch()
    }

}