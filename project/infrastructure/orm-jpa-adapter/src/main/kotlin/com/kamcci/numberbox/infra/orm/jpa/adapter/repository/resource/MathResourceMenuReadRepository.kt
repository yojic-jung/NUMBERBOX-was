package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.resource

import com.kamcci.numberbox.app.domain.vo.resource.MathResourceMenuVo
import com.kamcci.numberbox.app.port.orm.resource.MathResourceMenuReadOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.QMathResourceMenuEntity.mathResourceMenuEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.resource.MathResourceExpression
import org.springframework.stereotype.Repository

@Repository
class MathResourceMenuReadRepository(
    private val mathResourceExpression: MathResourceExpression
) : MathResourceMenuReadOrmPort, BaseRepository() {

    override fun readAll(): List<MathResourceMenuVo> {
        return queryFactory
            .select(mathResourceExpression.ceMathResourceMenuVo())
            .from(mathResourceMenuEntity)
            .orderBy(mathResourceMenuEntity.alignOrder.asc())
            .fetch()
    }
}