package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathContentsLikeEntity.mathContentsLikeEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MathContentsLikeReadRepository : BaseRepository() {
    fun readMemberIdListById(contentsId: Long): List<UUID> {
        return queryFactory
            .select(mathContentsLikeEntity.id.memberId)
            .from(mathContentsLikeEntity)
            .where(
                mathContentsLikeEntity.id.contentsId.eq(contentsId),
            )
            .fetch()
    }

    fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean {
        return queryFactory
            .selectOne()
            .from(mathContentsLikeEntity)
            .where(
                mathContentsLikeEntity.id.contentsId.eq(contentsId),
                mathContentsLikeEntity.id.memberId.eq(memberId),
            )
            .fetchOne() != null
    }

    fun countBy(contentsId: Long): Long {
        return queryFactory
            .select(mathContentsLikeEntity.id.count())
            .from(mathContentsLikeEntity)
            .where(
                mathContentsLikeEntity.id.contentsId.eq(contentsId),
            )
            .fetchFirst() ?: 0L
    }
}