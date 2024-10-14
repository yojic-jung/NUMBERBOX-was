package com.kamcci.numberbox.infra.orm.adapter.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.app.port.repository.math.MathContentsLikeModifyOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.math.MathContentsLikeDomain
import com.kamcci.numberbox.infra.orm.entity.math.MathContentsLikeEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsLikeEntity.mathContentsLikeEntity
import org.springframework.stereotype.Repository

@Repository
class MathContentsLikeModifyOrmAdapter : MathContentsLikeModifyOrmPort, BaseRepository() {
    override fun save(modifyDto: MathContentsLikeModifyDto): Boolean {
        val entity =
            MathContentsLikeEntity().apply { id = MathContentsLikeDomain(modifyDto.contentsId, modifyDto.memberId) }
        em.persist(entity)
        return em.contains(entity)
    }

    override fun delete(modifyDto: MathContentsLikeModifyDto): Boolean {
        return queryFactory
            .delete(mathContentsLikeEntity)
            .where(
                mathContentsLikeEntity.id.contentsId.eq(modifyDto.contentsId),
                mathContentsLikeEntity.id.memberId.eq(modifyDto.memberId),
            )
            .execute() > 0
    }
}