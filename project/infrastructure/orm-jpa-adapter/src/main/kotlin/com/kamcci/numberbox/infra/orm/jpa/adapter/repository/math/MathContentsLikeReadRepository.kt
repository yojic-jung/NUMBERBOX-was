package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.usecase.math.MathContentsLikeReadCase
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathContentsLikeEntity.mathContentsLikeEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MathContentsLikeReadRepository : MathContentsLikeReadCase, BaseRepository() {
    fun readMemberIdListById(contentsId: Long): List<UUID> {
        return queryFactory
            .select(mathContentsLikeEntity.id.memberId)
            .from(mathContentsLikeEntity)
            .where(
                mathContentsLikeEntity.id.contentsId.eq(contentsId),
            )
            .fetch()
    }

    override fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean {
        return queryFactory
            .selectOne()
            .from(mathContentsLikeEntity)
            .where(
                mathContentsLikeEntity.id.contentsId.eq(contentsId),
                mathContentsLikeEntity.id.memberId.eq(memberId),
            )
            .fetchOne() != null
    }

    override fun countBy(contentsId: Long): Long {
        return queryFactory
            .select(mathContentsLikeEntity.id.count())
            .from(mathContentsLikeEntity)
            .where(
                mathContentsLikeEntity.id.contentsId.eq(contentsId),
            )
            .fetchFirst() ?: 0L
    }
}