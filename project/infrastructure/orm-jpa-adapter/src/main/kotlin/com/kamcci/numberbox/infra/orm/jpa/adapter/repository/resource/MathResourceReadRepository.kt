package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.resource

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceVo
import com.kamcci.numberbox.app.port.orm.resource.MathResourceReadOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.QMathResourceCateEntity.mathResourceCateEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.QMathResourceEntity.mathResourceEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.resource.MathResourceExpression
import org.springframework.stereotype.Repository

@Repository
class MathResourceReadRepository(
    private val mathResourceExpression: MathResourceExpression
) : MathResourceReadOrmPort, BaseRepository() {
    override fun readByMainCateId(mainCateId: Int, pageReq: PageRequest): List<MathResourceVo> =
        queryFactory
            .select(mathResourceExpression.ceMathResourceVo())
            .from(mathResourceEntity)
            .innerJoin(mathResourceEntity.mathResourceCate, mathResourceCateEntity)
            .where(mathResourceCateEntity.mainCateId.eq(mainCateId))
            .offset(pageReq.getOffset())
            .limit(pageReq.pageVolume)
            .fetch()

    override fun countByMainCateId(mainCateId: Int): Long =
        queryFactory
            .select(mathResourceEntity.id.count())
            .from(mathResourceEntity)
            .innerJoin(mathResourceEntity.mathResourceCate, mathResourceCateEntity)
            .where(mathResourceCateEntity.mainCateId.eq(mainCateId))
            .fetchFirst()
}