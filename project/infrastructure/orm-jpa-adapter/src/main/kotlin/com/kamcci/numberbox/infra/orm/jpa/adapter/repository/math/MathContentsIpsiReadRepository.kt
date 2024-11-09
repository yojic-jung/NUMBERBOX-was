package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.port.orm.math.MathContentsIpsiReadOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathContentsIpsiSrcEntity.mathContentsIpsiSrcEntity
import org.springframework.stereotype.Repository

@Repository
class MathContentsIpsiReadRepository : MathContentsIpsiReadOrmPort, BaseRepository() {
    override fun readAllIpsiYear(): List<Int> {
        return queryFactory
            .selectDistinct(mathContentsIpsiSrcEntity.impYear)
            .from(mathContentsIpsiSrcEntity)
            .fetch()
    }
}