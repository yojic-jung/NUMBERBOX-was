package com.kamcci.numberbox.infra.orm.adapter.math

import com.kamcci.numberbox.app.domain.vo.math.MathCategoryUnitVo
import com.kamcci.numberbox.app.port.repository.math.MathUnitInfoReadOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.math.QMathCategoryUnitEntity.mathCategoryUnitEntity
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Repository

@Repository
class MathUnitInfoReadOrmAdapter : MathUnitInfoReadOrmPort, BaseRepository() {
    override fun readAll(): List<MathCategoryUnitVo> {
        return queryFactory
            .select(
                Projections.constructor(
                    MathCategoryUnitVo::class.java,
                    mathCategoryUnitEntity.id,
                    mathCategoryUnitEntity.subject,
                    mathCategoryUnitEntity.firUnit,
                    mathCategoryUnitEntity.secUnit,
                    mathCategoryUnitEntity.thrUnit
                )
            )
            .from(mathCategoryUnitEntity)
            .orderBy(mathCategoryUnitEntity.id.asc())
            .fetch()
    }
}