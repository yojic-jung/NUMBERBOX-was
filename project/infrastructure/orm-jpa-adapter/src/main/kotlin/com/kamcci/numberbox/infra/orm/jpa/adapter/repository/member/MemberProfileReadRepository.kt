package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.domain.vo.member.MemberProfileImgVo
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import com.kamcci.numberbox.app.port.orm.member.MemberProfileReadOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberProfileEntity.memberProfileEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.member.MemberProfileExpression
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberProfileReadRepository(
    private val memberProfileExpression: MemberProfileExpression
) : MemberProfileReadOrmPort, BaseRepository() {
    override fun readIdByMemberId(memberId: UUID): Long? {
        return queryFactory
            .select(memberProfileEntity.id)
            .from(memberProfileEntity)
            .where(memberProfileEntity.memberId.eq(memberId))
            .fetchFirst()
    }

    override fun readByMemberId(memberId: UUID): MemberProfileVo? {
        return queryFactory
            .select(memberProfileExpression.ceMemberProfileVo())
            .from(memberProfileEntity)
            .where(memberProfileEntity.memberId.eq(memberId))
            .fetchOne()
    }

    override fun readByProfileId(profileId: Long): MemberProfileVo? {
        return queryFactory
            .select(memberProfileExpression.ceMemberProfileVo())
            .from(memberProfileEntity)
            .where(memberProfileEntity.id.eq(profileId))
            .fetchOne()
    }

    override fun readProfileIdByMemberId(memberId: UUID): Long? {
        return queryFactory
            .select(memberProfileEntity.id)
            .from(memberProfileEntity)
            .where(memberProfileEntity.memberId.eq(memberId))
            .fetchOne()
    }

    override fun readProfileImgByMemberId(memberId: UUID): MemberProfileImgVo? {
        return queryFactory
            .select(memberProfileExpression.ceMemberProfileImgVo(memberId))
            .from(memberProfileEntity)
            .where(memberProfileEntity.memberId.eq(memberId))
            .fetchOne()
    }

    override fun readByProfileIdList(profileId: List<Long>): List<MemberProfileVo> {
        return queryFactory
            .select(memberProfileExpression.ceMemberProfileVo())
            .from(memberProfileEntity)
            .where(memberProfileEntity.id.`in`(profileId))
            .fetch()
    }

}