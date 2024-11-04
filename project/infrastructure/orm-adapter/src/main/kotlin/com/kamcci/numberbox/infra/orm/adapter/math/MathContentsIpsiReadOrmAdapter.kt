package com.kamcci.numberbox.infra.orm.adapter.math

import com.kamcci.numberbox.app.port.repository.math.MathContentsIpsiReadOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.math.QMathContentsIpsiSrcEntity.mathContentsIpsiSrcEntity
import org.springframework.stereotype.Repository

@Repository
class MathContentsIpsiReadOrmAdapter : MathContentsIpsiReadOrmPort, BaseRepository() {
    override fun readAllIpsiYear(): List<Int> {
        return queryFactory
            .selectDistinct(mathContentsIpsiSrcEntity.impYear)
            .from(mathContentsIpsiSrcEntity)
            .fetch()
    }
}