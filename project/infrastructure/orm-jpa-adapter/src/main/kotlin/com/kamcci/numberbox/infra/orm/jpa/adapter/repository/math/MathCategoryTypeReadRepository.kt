package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.domain.vo.math.MathCategoryTypeVo
import com.kamcci.numberbox.app.usecase.math.MathCategoryTypeReadCase
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathCategoryTypeEntity.mathCategoryTypeEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.math.MathCategoryExpression
import org.springframework.stereotype.Repository

@Repository
class MathCategoryTypeReadRepository(
    private val mathCategoryExpression: MathCategoryExpression
) : MathCategoryTypeReadCase, BaseRepository() {
    override fun readByUnitId(unitId: Int): List<MathCategoryTypeVo> {
        return queryFactory
            .select(mathCategoryExpression.ceMathCategoryTypeVo())
            .from(mathCategoryTypeEntity)
            .where(mathCategoryTypeEntity.mathTypeDomain.unitId.eq(unitId))
            .orderBy(mathCategoryTypeEntity.typeOrder.asc())
            .fetch()
    }

    override fun readByUnitId(unitIdList: List<Int>): List<MathCategoryTypeVo> {
        return queryFactory
            .select(mathCategoryExpression.ceMathCategoryTypeVo())
            .from(mathCategoryTypeEntity)
            .where(mathCategoryTypeEntity.mathTypeDomain.unitId.`in`(unitIdList))
            .orderBy(mathCategoryTypeEntity.typeOrder.asc())
            .fetch()
    }
}