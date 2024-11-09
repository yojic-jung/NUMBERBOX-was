package com.kamcci.numberbox.restapi.mapper.cs

import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportCreateDto
import com.kamcci.numberbox.restapi.dto.request.cs.CsErrorReportCreateRequest
import java.util.*

object CsErrorReportMapper {
    fun toCreateDto(reportMemberId: UUID, request: CsErrorReportCreateRequest): CsErrorReportCreateDto {
        val firImgFile = if (request.firstImgFile == null || request.firstImgFile.isEmpty) {
            null
        } else {
            request.firstImgFile.inputStream
        }

        val secImgFile = if (request.secondImgFile == null || request.secondImgFile.isEmpty) {
            null
        } else {
            request.secondImgFile.inputStream
        }

        val thrImgFile = if (request.thirdImgFile == null || request.thirdImgFile.isEmpty) {
            null
        } else {
            request.thirdImgFile.inputStream
        }
        return CsErrorReportCreateDto(
            errType = request.errType,
            contentsId = request.contentsId,
            reportMemberId = reportMemberId,
            reportContents = request.reportContents,
            clientOs = request.clientOs,
            clientBrowser = request.clientBrowser,
            firstImgName = request.firstImgFile?.originalFilename,
            firstImg = firImgFile,
            secondImgName = request.secondImgFile?.originalFilename,
            secondImg = secImgFile,
            thirdImgName = request.thirdImgFile?.originalFilename,
            thirdImg = thrImgFile,
        )
    }
}