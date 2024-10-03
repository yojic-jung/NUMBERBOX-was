package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.domain.vo.member.MemberEmailVerifyCodeVo
import com.kamcci.numberbox.app.port.repository.member.MemberVerifyCodeReadOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.QMemberVerifyCodeEntity.memberVerifyCodeEntity
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Repository

@Repository
class MemberVerifyCodeReadOrmAdapter : MemberVerifyCodeReadOrmPort, BaseRepository() {

    override fun countByEmailAndCodeType(email: String, codeType: VerifyCodeType): Long {
        return queryFactory
            .select(memberVerifyCodeEntity.count())
            .from(memberVerifyCodeEntity)
            .where(
                memberVerifyCodeEntity.email.eq(email),
                memberVerifyCodeEntity.codeType.eq(codeType)
            )
            .fetchFirst()
    }

    override fun findByEmailAndCodeType(email: String, codeType: VerifyCodeType): MemberEmailVerifyCodeVo? {
        return queryFactory
            .select(
                Projections.constructor(
                    MemberEmailVerifyCodeVo::class.java,
                    memberVerifyCodeEntity.verifyCode,
                    memberVerifyCodeEntity.sysCreateTime
                )
            )
            .from(memberVerifyCodeEntity)
            .where(
                memberVerifyCodeEntity.email.eq(email),
                memberVerifyCodeEntity.codeType.eq(codeType)
            )
            .fetchOne()
    }
}