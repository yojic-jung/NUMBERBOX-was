package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.port.orm.math.MathContentsLikeReadOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathContentsLikeEntity.mathContentsLikeEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MathContentsLikeReadRepository : MathContentsLikeReadOrmPort, BaseRepository() {
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
}