package com.kamcci.numberbox.infra.orm.adapter.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.app.port.repository.math.MathContentsRepoModifyOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.math.MathContentsRepositoryDomain
import com.kamcci.numberbox.infra.orm.entity.math.MathContentsRepositoryEntity
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsRepositoryEntity.mathContentsRepositoryEntity
import org.springframework.stereotype.Repository

@Repository
class MathContentsRepoModifyOrmAdapter : MathContentsRepoModifyOrmPort, BaseRepository() {
    override fun save(modifyDto: MathContentsRepoModifyDto): Boolean {
        val domainId = MathContentsRepositoryDomain(modifyDto.contentsId, modifyDto.memberId)
        val entity = MathContentsRepositoryEntity().apply { id = domainId }

        em.persist(entity)
        return em.contains(entity)
    }

    override fun delete(modifyDto: MathContentsRepoModifyDto): Boolean {
        return queryFactory
            .delete(mathContentsRepositoryEntity)
            .where(
                mathContentsRepositoryEntity.id.contentsId.eq(modifyDto.contentsId),
                mathContentsRepositoryEntity.id.memberId.eq(modifyDto.memberId),
            )
            .execute() > 0
    }
}