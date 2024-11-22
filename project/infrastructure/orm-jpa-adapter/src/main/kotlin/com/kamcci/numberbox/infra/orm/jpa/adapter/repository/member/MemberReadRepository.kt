package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.port.orm.member.MemberReadOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberEntity.memberEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberPrivateEntity.memberPrivateEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberReadRepository : MemberReadOrmPort, BaseRepository() {
    override fun readIdByEmail(email: String) =
        queryFactory
            .select(memberEntity.id)
            .from(memberEntity)
            .where(memberEntity.email.eq(email))
            .fetchOne()

    override fun readEmailByUsernameAndPhone(userName: String, phoneNumber: String): String? =
        queryFactory
            .select(memberEntity.email)
            .from(memberEntity)
            .innerJoin(memberPrivateEntity)
            .on(memberEntity.id.eq(memberPrivateEntity.memberId))
            .where(
                memberPrivateEntity.userName.eq(userName),
                memberPrivateEntity.phoneNumber.eq(phoneNumber),
            )
            .fetchOne()

    override fun existEmail(email: String): Boolean =
        queryFactory
            .selectOne()
            .from(memberEntity)
            .where(memberEntity.email.eq(email))
            .fetchOne() != null

    override fun readPasswordByMemberId(memberId: UUID): String? =
        queryFactory
            .select(memberEntity.password)
            .from(memberEntity)
            .where(memberEntity.id.eq(memberId))
            .fetchOne()

    override fun readFailCountById(id: UUID) =
        queryFactory
            .select(memberEntity.failCount)
            .from(memberEntity)
            .where(memberEntity.id.eq(id))
            .fetchOne()

    override fun readLastFailTimeById(id: UUID) =
        queryFactory
            .select(memberEntity.lastFailTime)
            .from(memberEntity)
            .where(memberEntity.id.eq(id))
            .fetchOne()

    override fun existsByEmail(email: String): Boolean =
        queryFactory
            .selectOne()
            .from(memberEntity)
            .where(memberEntity.email.eq(email))
            .fetchFirst() != null

    override fun readByIsTmpPassword(isTrue: Boolean, limit: Long): List<UUID> =
        queryFactory
            .select(memberEntity.id)
            .from(memberEntity)
            .limit(limit)
            .where(memberEntity.isTmpPassword.eq(isTrue))
            .fetch()
}