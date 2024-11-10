package com.kamcci.numberbox.infra.orm.jpa.adapter.util.member

import com.kamcci.numberbox.app.domain.vo.member.MemberProfileImgVo
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberProfileEntity.memberProfileEntity
import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import org.springframework.stereotype.Component
import java.util.*

@Component
class MemberProfileExpression {
    fun ceMemberProfileVo(): ConstructorExpression<MemberProfileVo> =
        Projections.constructor(
            MemberProfileVo::class.java,
            memberProfileEntity.id,
            memberProfileEntity.memberId,
            memberProfileEntity.nickname,
            memberProfileEntity.profileImgName,
            memberProfileEntity.profileImgPath,
            memberProfileEntity.profileType,
        )

    fun ceMemberProfileImgVo(memberId: UUID): ConstructorExpression<MemberProfileImgVo> =
        Projections.constructor(
            MemberProfileImgVo::class.java,
            memberProfileEntity.id,
            Expressions.constant(memberId),
            memberProfileEntity.profileImgPath,
            memberProfileEntity.profileImgName,
        )
}