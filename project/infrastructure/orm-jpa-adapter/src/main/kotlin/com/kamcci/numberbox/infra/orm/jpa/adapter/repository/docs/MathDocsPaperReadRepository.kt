package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.docs

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import com.kamcci.numberbox.app.domain.vo.docs.MathDocsPaperVo
import com.kamcci.numberbox.app.port.orm.docs.MathDocsPaperReadOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.docs.QMathDocsPaperEntity.mathDocsPaperEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.docs.MathDocsExpression
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MathDocsPaperReadRepository(
    private val mathDocsExpression: MathDocsExpression
) : MathDocsPaperReadOrmPort, BaseRepository() {

    override fun readByIdAndMemberId(id: Long, memberId: UUID): MathDocsPaperVo? {
        val commonQuery = commonMathDocsPaperQuery()
        return commonQuery
            .where(
                mathDocsPaperEntity.id.eq(id),
                mathDocsPaperEntity.memberId.eq(memberId),
                mathDocsPaperEntity.sysDeleteDate.isNull
            )
            .fetchOne()

    }

    override fun readByMemberId(memberId: UUID, pageReq: PageRequest): List<MathDocsPaperVo> {
        val commonQuery = commonMathDocsPaperQuery()
        return commonQuery
            .where(
                mathDocsPaperEntity.memberId.eq(memberId),
                mathDocsPaperEntity.docsStts.`in`(DocsStatusType.None, DocsStatusType.Self),
                mathDocsPaperEntity.sysDeleteDate.isNull
            )
            .offset(pageReq.getOffset())
            .limit(pageReq.pageVolume)
            .orderBy(mathDocsPaperEntity.id.desc())
            .fetch()
    }

    override fun countByMemberId(memberId: UUID): Long =
        queryFactory
            .select(mathDocsPaperEntity.id.count())
            .from(mathDocsPaperEntity)
            .where(
                mathDocsPaperEntity.memberId.eq(memberId),
                mathDocsPaperEntity.docsStts.`in`(DocsStatusType.None, DocsStatusType.Self),
                mathDocsPaperEntity.sysDeleteDate.isNull
            )
            .fetchFirst()

    private fun commonMathDocsPaperQuery() =
        queryFactory
            .select(
                Projections.constructor(
                    MathDocsPaperVo::class.java,
                    mathDocsPaperEntity.id,
                    mathDocsPaperEntity.contentsIdList,
                    mathDocsPaperEntity.docsGrade,
                    mathDocsPaperEntity.docsTitle,
                    mathDocsPaperEntity.docsSubTitle,
                    mathDocsPaperEntity.docsOwner,
                    mathDocsPaperEntity.docsStts,
                    mathDocsPaperEntity.sysCreateDate,
                    mathDocsPaperEntity.sysUpdateDate,
                )
            )
            .from(mathDocsPaperEntity)

}