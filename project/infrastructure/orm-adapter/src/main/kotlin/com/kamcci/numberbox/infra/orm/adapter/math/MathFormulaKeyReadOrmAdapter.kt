package com.kamcci.numberbox.infra.orm.adapter.math

import com.kamcci.numberbox.app.domain.vo.math.MathFormulaKeyVo
import com.kamcci.numberbox.app.port.repository.math.MathFormulaKeyReadOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.math.QMathFormulaKeyEntity.mathFormulaKeyEntity
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Repository

@Repository
class MathFormulaKeyReadOrmAdapter : MathFormulaKeyReadOrmPort, BaseRepository() {
    override fun findAll(): List<MathFormulaKeyVo> = queryFactory
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