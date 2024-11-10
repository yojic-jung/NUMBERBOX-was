package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.cs

import com.kamcci.numberbox.app.domain.vo.cs.CsErrReportVo
import com.kamcci.numberbox.app.port.orm.cs.CsErrorReportReadOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.cs.QCsErrorReportEntity.csErrorReportEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.cs.CsErrorReportExpression
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CsErrorReportReadRepository(
    private val csErrorReportExpression: CsErrorReportExpression
) : CsErrorReportReadOrmPort, BaseRepository() {

    override fun readByMemberId(memberId: UUID): List<CsErrReportVo> {
        return queryFactory
            .select(csErrorReportExpression.ceCsErrReportVo())
            .from(csErrorReportEntity)
            .where(csErrorReportEntity.reportMemberId.eq(memberId))
            .orderBy(csErrorReportEntity.id.desc())
            .fetch()
    }
}