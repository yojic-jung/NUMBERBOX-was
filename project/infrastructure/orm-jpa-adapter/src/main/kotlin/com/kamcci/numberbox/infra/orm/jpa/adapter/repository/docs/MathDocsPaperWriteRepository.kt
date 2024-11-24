package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperUpdtDto
import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import com.kamcci.numberbox.app.port.orm.docs.MathDocsPaperWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.docs.QMathDocsPaperEntity.mathDocsPaperEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.docs.MathDocsPaperFactory
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class MathDocsPaperWriteRepository : MathDocsPaperWriteOrmPort, BaseRepository() {
    override fun create(memberId: UUID, createDto: MathDocsPaperCreateDto): Long {
        val saveEntity = MathDocsPaperFactory.getSaveEntity(memberId, createDto)
        em.persist(saveEntity)
        return saveEntity.id
    }

    override fun update(memberId: UUID, updtDto: MathDocsPaperUpdtDto): Long {
        return queryFactory
            .update(mathDocsPaperEntity)
            .set(mathDocsPaperEntity.contentsIdList, updtDto.contentsIdList)
            .set(mathDocsPaperEntity.docsGrade, updtDto.docsGrade)
            .set(mathDocsPaperEntity.docsTitle, updtDto.docsTitle)
            .set(mathDocsPaperEntity.docsSubTitle, updtDto.docsSubTitle)
            .set(mathDocsPaperEntity.docsOwner, updtDto.docsOwner)
            .set(mathDocsPaperEntity.docsStts, updtDto.docsStts)
            .set(mathDocsPaperEntity.sysUpdateDate, LocalDateTime.now())
            .where(
                mathDocsPaperEntity.id.eq(updtDto.id),
                mathDocsPaperEntity.memberId.eq(memberId)
            )
            .execute()

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