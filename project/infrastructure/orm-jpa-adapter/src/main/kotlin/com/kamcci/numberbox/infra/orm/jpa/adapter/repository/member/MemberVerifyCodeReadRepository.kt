package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.domain.vo.member.MemberEmailVerifyCodeVo
import com.kamcci.numberbox.app.port.orm.member.MemberVerifyCodeReadOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberVerifyCodeEntity.memberVerifyCodeEntity
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Repository

@Repository
class MemberVerifyCodeReadRepository : MemberVerifyCodeReadOrmPort, BaseRepository() {

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

    override fun readByEmailAndCodeType(email: String, codeType: VerifyCodeType): MemberEmailVerifyCodeVo? {
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