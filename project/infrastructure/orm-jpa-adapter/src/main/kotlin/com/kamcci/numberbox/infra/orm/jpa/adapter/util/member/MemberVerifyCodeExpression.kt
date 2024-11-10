package com.kamcci.numberbox.infra.orm.jpa.adapter.util.member

import com.kamcci.numberbox.app.domain.vo.member.MemberVerifyCodeVo
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberVerifyCodeEntity.memberVerifyCodeEntity
import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Component

@Component
class MemberVerifyCodeExpression {
    fun ceMemberVerifyCodeVo(): ConstructorExpression<MemberVerifyCodeVo> =
        Projections.constructor(
            MemberVerifyCodeVo::class.java,
            memberVerifyCodeEntity.verifyCode,
            memberVerifyCodeEntity.sysCreateTime
        )
}