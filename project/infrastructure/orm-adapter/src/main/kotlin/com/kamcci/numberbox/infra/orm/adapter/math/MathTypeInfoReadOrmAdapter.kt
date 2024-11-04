package com.kamcci.numberbox.infra.orm.adapter.math

import com.kamcci.numberbox.app.domain.vo.math.MathTypeInfoVo
import com.kamcci.numberbox.app.port.repository.math.MathTypeInfoReadOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.math.QMathTypeInfoEntity.mathTypeInfoEntity
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Repository

@Repository
class MathTypeInfoReadOrmAdapter : MathTypeInfoReadOrmPort, BaseRepository() {
    override fun readByUnitId(unitId: Int): List<MathTypeInfoVo> {
        return queryFactory
            .select(
                Projections.constructor(
                    MathTypeInfoVo::class.java,
                    mathTypeInfoEntity.mathTypeDomain.unitId,
                    mathTypeInfoEntity.mathTypeDomain.typeId,
                    mathTypeInfoEntity.quesType,
                    mathTypeInfoEntity.typeOrder,
                )
            )
            .from(mathTypeInfoEntity)
            .where(mathTypeInfoEntity.mathTypeDomain.unitId.eq(unitId))
            .orderBy(mathTypeInfoEntity.typeOrder.asc())
            .fetch()
    }

    override fun readByUnitId(unitIdList: List<Int>): List<MathTypeInfoVo> {
        return queryFactory
            .select(
                Projections.constructor(
                    MathTypeInfoVo::class.java,
                    mathTypeInfoEntity.mathTypeDomain.unitId,
                    mathTypeInfoEntity.mathTypeDomain.typeId,
                    mathTypeInfoEntity.quesType,
                    mathTypeInfoEntity.typeOrder,
                )
            )
            .from(mathTypeInfoEntity)
            .where(mathTypeInfoEntity.mathTypeDomain.unitId.`in`(unitIdList))
            .orderBy(mathTypeInfoEntity.typeOrder.asc())
            .fetch()
    }
}