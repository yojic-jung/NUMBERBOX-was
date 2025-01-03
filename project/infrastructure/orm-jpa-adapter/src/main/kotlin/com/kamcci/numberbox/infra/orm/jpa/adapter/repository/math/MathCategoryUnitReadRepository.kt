package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.domain.vo.math.MathCategoryUnitVo
import com.kamcci.numberbox.app.usecase.math.MathCategoryUnitReadCase
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathCategoryUnitEntity.mathCategoryUnitEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.math.MathCategoryExpression
import org.springframework.stereotype.Repository

@Repository
class MathCategoryUnitReadRepository(
    private val mathCategoryExpression: MathCategoryExpression
) : MathCategoryUnitReadCase, BaseRepository() {
    override fun readAll(): List<MathCategoryUnitVo> {
        return queryFactory
            .select(mathCategoryExpression.ceMathCategoryUnitVo())
            .from(mathCategoryUnitEntity)
            .orderBy(mathCategoryUnitEntity.id.asc())
            .fetch()
    }
}