package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.domain.vo.math.MathCategoryUnitVo
import com.kamcci.numberbox.app.port.orm.math.MathUnitInfoReadOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathCategoryUnitEntity.mathCategoryUnitEntity
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Repository

@Repository
class MathUnitInfoReadOrmRepository : MathUnitInfoReadOrmPort, BaseRepository() {
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