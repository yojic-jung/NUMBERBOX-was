package com.kamcci.numberbox.app.service.cs

import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportCreateDto
import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.cs.CsErrorReportWriteOrmPort
import com.kamcci.numberbox.app.port.orm.docs.MathDocsPaperWriteOrmPort
import com.kamcci.numberbox.app.usecase.cs.CsErrorReportWriteUseCase

@UseCase
class CsErrorReportWriteService(
    private val csErrorReportWriteOrmPort: CsErrorReportWriteOrmPort,
    private val mathDocsPaperWriteOrmPort: MathDocsPaperWriteOrmPort,
) : CsErrorReportWriteUseCase {
    @TXExecute
    override fun createReport(createDto: CsErrorReportCreateDto): Long {
        // 학습지 에러인 경우
        if (createDto.errType == CSErrorType.MathDocs) {
            // 학습지 상태 변경
            mathDocsPaperWriteOrmPort.updateDocsSttsByIdAndMemberId(
                createDto.contentsId!!,
                createDto.reportMemberId,
                DocsStatusType.Self
            )
        }

        // 문의 신고 저장
        return csErrorReportWriteOrmPort.create(createDto)
    }


}