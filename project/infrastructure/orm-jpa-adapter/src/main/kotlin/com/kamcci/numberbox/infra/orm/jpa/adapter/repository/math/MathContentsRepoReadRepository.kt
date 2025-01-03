package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.usecase.math.MathContentsRepoReadCase
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathContentsRepositoryEntity.mathContentsRepositoryEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MathContentsRepoReadRepository : MathContentsRepoReadCase, BaseRepository() {
    override fun readContentsIdByMemberId(memberId: UUID): List<Long> {
        return queryFactory
            .select(mathContentsRepositoryEntity.id.contentsId)
            .from(mathContentsRepositoryEntity)
            .where(mathContentsRepositoryEntity.id.memberId.eq(memberId))
            .fetch()
    }

    override fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean {
        return queryFactory
            .selectOne()
            .from(mathContentsRepositoryEntity)
            .where(
                mathContentsRepositoryEntity.id.contentsId.eq(contentsId),
                mathContentsRepositoryEntity.id.memberId.eq(memberId),
            )
            .fetchOne() != null
    }
}