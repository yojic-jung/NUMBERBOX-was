package com.kamcci.numberbox.infra.orm.adapter.math

import com.kamcci.numberbox.app.domain.vo.math.MathUnitInfoVo
import com.kamcci.numberbox.app.port.repository.math.MathUnitInfoReadOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.math.QMathUnitInfoEntity.mathUnitInfoEntity
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Repository

@Repository
class MathUnitInfoReadOrmAdapter : MathUnitInfoReadOrmPort, BaseRepository() {
    override fun findAll(): List<MathUnitInfoVo> {
        return queryFactory
            .select(
                Projections.constructor(
                    MathUnitInfoVo::class.java,
                    mathUnitInfoEntity.id,
                    mathUnitInfoEntity.subject,
                    mathUnitInfoEntity.firUnit,
                    mathUnitInfoEntity.secUnit,
                    mathUnitInfoEntity.thrUnit
                )
            )
            .from(mathUnitInfoEntity)
            .orderBy(mathUnitInfoEntity.id.asc())
            .fetch()
    }
}