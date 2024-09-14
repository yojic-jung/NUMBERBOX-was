package com.kamcci.numberbox.infra.orm.repository.member

import com.kamcci.numberbox.app.repository.member.EmailIDCodeQueryRepository
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.QMemberEmailIdCodeEntity.memberEmailIdCodeEntity

class EmailIDCodeQueryRepositoryImpl : EmailIDCodeQueryRepository, BaseRepository() {

    override fun countByEmail(email: String): Long {
        return queryFactory
            .select(memberEmailIdCodeEntity.count())
            .from(memberEmailIdCodeEntity)
            .where(memberEmailIdCodeEntity.email.eq(email))
            .fetchFirst()
    }
}