package com.kamcci.numberbox.infra.orm.jpa.adapter.util.cs

import com.kamcci.numberbox.app.domain.vo.cs.CsErrReportVo
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.cs.QCsErrorReportEntity.csErrorReportEntity
import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Component

@Component
class CsErrorReportExpression {
    fun ceCsErrReportVo(): ConstructorExpression<CsErrReportVo> =
        Projections.constructor(
            CsErrReportVo::class.java,
            csErrorReportEntity.id,
            csErrorReportEntity.errType,
            csErrorReportEntity.contentsId,
            csErrorReportEntity.reportContents,
            csErrorReportEntity.replyContents,
            csErrorReportEntity.firstImgPath,
            csErrorReportEntity.firstImgName,
            csErrorReportEntity.secondImgPath,
            csErrorReportEntity.secondImgName,
            csErrorReportEntity.thirdImgPath,
            csErrorReportEntity.thirdImgName,
            csErrorReportEntity.reportStts,
            csErrorReportEntity.sysCreateDate,
            csErrorReportEntity.sysUpdateDate,
        )
}