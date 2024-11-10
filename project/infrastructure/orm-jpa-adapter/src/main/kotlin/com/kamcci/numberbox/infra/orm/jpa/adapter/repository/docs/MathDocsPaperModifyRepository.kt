package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperUpdtDto
import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.port.orm.docs.MathDocsPaperModifyOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.docs.MathDocsPaperEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.docs.QMathDocsPaperEntity.mathDocsPaperEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.docs.MathDocsPaperFactory
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class MathDocsPaperModifyRepository : MathDocsPaperModifyOrmPort, BaseRepository() {
    override fun create(memberId: UUID, createDto: MathDocsPaperCreateDto): Long {
        val saveEntity = MathDocsPaperFactory.getSaveEntity(memberId, createDto)
        em.persist(saveEntity)
        return saveEntity.id
    }

    override fun update(memberId: UUID, updtDto: MathDocsPaperUpdtDto): Long {
        val orgEntity = em.find(MathDocsPaperEntity::class.java, updtDto.id)
        if (orgEntity.memberId != memberId) throw BusinessValidException("자신의 문제가 아닌 경우 학습지 수정이 불가합니다.")

        val updtEntity = MathDocsPaperFactory.getUpdtEntity(orgEntity, updtDto)
        em.persist(updtEntity)
        return updtEntity.id
    }

    override fun updateDocsSttsByIdAndMemberId(docsId: Long, memberId: UUID, docsStts: DocsStatusType): Long {
        return queryFactory
            .update(mathDocsPaperEntity)
            .set(mathDocsPaperEntity.docsStts, docsStts)
            .where(
                mathDocsPaperEntity.id.eq(docsId),
                mathDocsPaperEntity.memberId.eq(memberId)
            )
            .execute()
    }

    override fun delete(docsId: Long, memberId: UUID): Long {
        return queryFactory
            .update(mathDocsPaperEntity)
            .set(mathDocsPaperEntity.sysDeleteDate, LocalDateTime.now())
            .where(
                mathDocsPaperEntity.id.eq(docsId),
                mathDocsPaperEntity.memberId.eq(memberId)
            )
            .execute()
    }
}