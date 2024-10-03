package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.port.repository.member.MemberReadOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.QMemberEntity.memberEntity
import com.kamcci.numberbox.infra.orm.entity.member.QMemberPrivateEntity.memberPrivateEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberReadOrmAdapter : MemberReadOrmPort, BaseRepository() {
    override fun findIdByEmail(email: String) =
        queryFactory
            .select(memberEntity.id)
            .from(memberEntity)
            .where(memberEntity.email.eq(email))
            .fetchOne()

    override fun findEmailByUsernameAndPhone(userName: String, phoneNumber: String): String? =
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

    override fun findFailCountById(id: UUID) =
        queryFactory
            .select(memberEntity.failCount)
            .from(memberEntity)
            .where(memberEntity.id.eq(id))
            .fetchOne()

    override fun findLastFailTimeById(id: UUID) =
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
}