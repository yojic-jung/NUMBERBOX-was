package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.domain.vo.math.MathFormulaKeyVo
import com.kamcci.numberbox.app.usecase.math.MathFormulaKeyReadCase
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathFormulaKeyEntity.mathFormulaKeyEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.math.MathFormulaExpression
import org.springframework.stereotype.Repository

@Repository
class MathFormulaKeyReadRepository(
    private val mathFormulaExpression: MathFormulaExpression
) : MathFormulaKeyReadCase, BaseRepository() {
    override fun readAll(): List<MathFormulaKeyVo> = queryFactory
        .select(mathFormulaExpression.ceMathFormulaKeyVo())
        .from(mathFormulaKeyEntity)
        .orderBy(
            mathFormulaKeyEntity.formulOrder.asc(),
            mathFormulaKeyEntity.id.asc()
        )
        .fetch()

}