package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.cs

import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportCreateDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.cs.CsErrorReportEntity

object CsErrorReportFactory {
    fun getSaveEntity(createDto: CsErrorReportCreateDto): CsErrorReportEntity {
        return CsErrorReportEntity().apply {
            errType = createDto.errType
            contentsId = createDto.contentsId
            reportMemberId = createDto.reportMemberId
            reportContents = createDto.reportContents
            clientOs = createDto.clientOs
            clientBrowser = createDto.clientBrowser
            firstImgPath = createDto.firstImgPath
            firstImgName = createDto.firstImgName
            secondImgPath = createDto.secondImgPath
            secondImgName = createDto.secondImgName
            thirdImgPath = createDto.thirdImgPath
            thirdImgName = createDto.thirdImgName
            reportStts = createDto.reportStts
        }
    }
}