package com.kamcci.numberbox.infra.orm.repository.member

import com.kamcci.numberbox.app.domain.dto.member.EmailVerifyCodeVo
import com.kamcci.numberbox.app.repository.member.EmailVerifyCodeReadRepository
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.QMemberEmailVerifyCodeEntity.memberEmailVerifyCodeEntity
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Repository

@Repository
class EmailVerifyCodeReadRepositoryImpl : EmailVerifyCodeReadRepository, BaseRepository() {

    override fun countByEmail(email: String): Long {
        return queryFactory
            .select(memberEmailVerifyCodeEntity.count())
            .from(memberEmailVerifyCodeEntity)
            .where(memberEmailVerifyCodeEntity.email.eq(email))
            .fetchFirst()
    }

    override fun findByEmail(email: String): EmailVerifyCodeVo? {
        return queryFactory
            .select(
                Projections.constructor(
                    EmailVerifyCodeVo::class.java,
                    memberEmailVerifyCodeEntity.verifyCode,
                    memberEmailVerifyCodeEntity.sysCreateTime
                )
            )
            .from(memberEmailVerifyCodeEntity)
            .where(memberEmailVerifyCodeEntity.email.eq(email))
            .fetchOne()
    }
}