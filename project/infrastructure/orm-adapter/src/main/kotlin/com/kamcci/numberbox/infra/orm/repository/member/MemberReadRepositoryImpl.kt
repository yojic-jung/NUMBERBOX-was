package com.kamcci.numberbox.infra.orm.repository.member

import com.kamcci.numberbox.app.repository.member.MemberReadRepository
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.QMemberEntity.memberEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberReadRepositoryImpl : MemberReadRepository, BaseRepository() {
    override fun findIdByEmail(email: String) =
        queryFactory
            .select(memberEntity.id)
            .from(memberEntity)
            .where(memberEntity.email.eq(email))
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