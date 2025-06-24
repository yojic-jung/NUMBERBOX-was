package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.app.port.orm.math.MathContentsLikeWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.MathContentsLikeDomain
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.MathContentsLikeEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathContentsLikeEntity.mathContentsLikeEntity
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository

@Qualifier("jpa")
@Repository
class MathContentsLikeWriteRepository : MathContentsLikeWriteOrmPort, BaseRepository() {
    override fun save(modifyDto: MathContentsLikeModifyDto): Boolean {
        val entity = MathContentsLikeEntity().apply {
            id = MathContentsLikeDomain(modifyDto.contentsId, modifyDto.memberId)
        }
        em.persist(entity)
        return em.contains(entity)
    }

    override fun delete(modifyDto: MathContentsLikeModifyDto): Long {
        return queryFactory
            .delete(mathContentsLikeEntity)
            .where(
                mathContentsLikeEntity.id.contentsId.eq(modifyDto.contentsId),
                mathContentsLikeEntity.id.memberId.eq(modifyDto.memberId),
            )
            .execute()
    }

}