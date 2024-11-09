package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.cs

import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportSaveDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.cs.CsErrorReportEntity

object CsErrorReportFactory {
    fun getSaveEntity(saveDto: CsErrorReportSaveDto): CsErrorReportEntity {
        return CsErrorReportEntity().apply {
            errType = saveDto.errType
            contentsId = saveDto.contentsId
            reportMemberId = saveDto.reportMemberId
            reportContents = saveDto.reportContents
            clientOs = saveDto.clientOs
            clientBrowser = saveDto.clientBrowser
            firstImgPath = saveDto.firstImgPath
            firstImgName = saveDto.firstImgName
            secondImgPath = saveDto.secondImgPath
            secondImgName = saveDto.secondImgName
            thirdImgPath = saveDto.thirdImgPath
            thirdImgName = saveDto.thirdImgName
            reportStts = saveDto.reportStts
        }
    }
}