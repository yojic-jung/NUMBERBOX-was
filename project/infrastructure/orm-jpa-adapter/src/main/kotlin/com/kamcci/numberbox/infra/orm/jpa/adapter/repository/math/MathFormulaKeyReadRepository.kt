package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.domain.vo.math.MathFormulaKeyVo
import com.kamcci.numberbox.app.port.orm.math.MathFormulaKeyReadOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathFormulaKeyEntity.mathFormulaKeyEntity
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Repository

@Repository
class MathFormulaKeyReadRepository : MathFormulaKeyReadOrmPort, BaseRepository() {
    override fun readAll(): List<MathFormulaKeyVo> = queryFactory
        .select(
            Projections.constructor(
                MathFormulaKeyVo::class.java,
                mathFormulaKeyEntity.id,
                mathFormulaKeyEntity.formulOrder,
                mathFormulaKeyEntity.formulName,
                mathFormulaKeyEntity.formulUi,
                mathFormulaKeyEntity.shortcutKey,
                mathFormulaKeyEntity.latexGrammer,
                mathFormulaKeyEntity.nbGrammer,
                mathFormulaKeyEntity.guide,
                mathFormulaKeyEntity.shortcutKeycode,
                mathFormulaKeyEntity.texGrammer,
                mathFormulaKeyEntity.lineChange,
                mathFormulaKeyEntity.classification,
            )
        )
        .from(mathFormulaKeyEntity)
        .orderBy(
            mathFormulaKeyEntity.formulOrder.asc(),
            mathFormulaKeyEntity.id.asc()
        )
        .fetch()

}