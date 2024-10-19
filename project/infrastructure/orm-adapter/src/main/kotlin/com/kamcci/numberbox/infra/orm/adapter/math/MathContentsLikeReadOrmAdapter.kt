package com.kamcci.numberbox.infra.orm.adapter.math

import com.kamcci.numberbox.app.port.repository.math.MathContentsLikeReadOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsLikeEntity.mathContentsLikeEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MathContentsLikeReadOrmAdapter : MathContentsLikeReadOrmPort, BaseRepository() {
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