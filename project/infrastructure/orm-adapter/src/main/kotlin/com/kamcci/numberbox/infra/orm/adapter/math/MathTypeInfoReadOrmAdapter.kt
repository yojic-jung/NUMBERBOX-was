package com.kamcci.numberbox.infra.orm.adapter.math

import com.kamcci.numberbox.app.domain.vo.math.MathCategoryTypeVo
import com.kamcci.numberbox.app.port.orm.math.MathTypeInfoReadOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.math.QMathCategoryTypeEntity.mathCategoryTypeEntity
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Repository

@Repository
class MathTypeInfoReadOrmAdapter : MathTypeInfoReadOrmPort, BaseRepository() {
    override fun readByUnitId(unitId: Int): List<MathCategoryTypeVo> {
        return queryFactory
            .select(
                Projections.constructor(
                    MathCategoryTypeVo::class.java,
                    mathCategoryTypeEntity.mathTypeDomain.unitId,
                    mathCategoryTypeEntity.mathTypeDomain.typeId,
                    mathCategoryTypeEntity.quesType,
                    mathCategoryTypeEntity.typeOrder,
                )
            )
            .from(mathCategoryTypeEntity)
            .where(mathCategoryTypeEntity.mathTypeDomain.unitId.eq(unitId))
            .orderBy(mathCategoryTypeEntity.typeOrder.asc())
            .fetch()
    }

    override fun readByUnitId(unitIdList: List<Int>): List<MathCategoryTypeVo> {
        return queryFactory
            .select(
                Projections.constructor(
                    MathCategoryTypeVo::class.java,
                    mathCategoryTypeEntity.mathTypeDomain.unitId,
                    mathCategoryTypeEntity.mathTypeDomain.typeId,
                    mathCategoryTypeEntity.quesType,
                    mathCategoryTypeEntity.typeOrder,
                )
            )
            .from(mathCategoryTypeEntity)
            .where(mathCategoryTypeEntity.mathTypeDomain.unitId.`in`(unitIdList))
            .orderBy(mathCategoryTypeEntity.typeOrder.asc())
            .fetch()
    }
}