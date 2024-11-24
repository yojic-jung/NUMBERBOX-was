package com.kamcci.numberbox.restapi.controller.cs

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportCreateDto
import com.kamcci.numberbox.app.domain.enumeration.cs.ReportSttsType
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.usecase.common.FileUseCase
import com.kamcci.numberbox.app.usecase.cs.CsErrorReportModifyUseCase
import com.kamcci.numberbox.restapi.dto.request.cs.CsErrorReportCreateRequest
import com.kamcci.numberbox.restapi.util.file.FileUtil.toFile
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * 고객센터 - 변경
 */
@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/cs/error")
class CsErrorReportModifyController(
    private val fileUseCase: FileUseCase,
    private val csErrorReportModifyUseCase: CsErrorReportModifyUseCase
) {
    /**
     * 고객센터 신고하기
     */
    @PostMapping
    fun create(
        @UserId
        memberId: UUID,
        @ModelAttribute reqBody: CsErrorReportCreateRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 이미지 업로드(최대 세장)
        val firImgNameVo =
            if (reqBody.firstImgFile != null) fileUseCase.upload(toFile(reqBody.firstImgFile), FileType.CsErrIMG)
            else null
        val secImgVo =
            if (reqBody.secondImgFile != null) fileUseCase.upload(toFile(reqBody.secondImgFile), FileType.CsErrIMG)
            else null
        val thrImgVo =
            if (reqBody.thirdImgFile != null) fileUseCase.upload(toFile(reqBody.thirdImgFile), FileType.CsErrIMG)
            else null

        val createDto = toCreateDto(memberId, reqBody, firImgNameVo, secImgVo, thrImgVo)
        return ResponseUtil.ok(mapOf("csErrorReportId" to csErrorReportModifyUseCase.createReport(createDto)))
    }

    private fun toCreateDto(
        memberId: UUID,
        reqBody: CsErrorReportCreateRequest,
        firImg: FileNameVo?,
        secImg: FileNameVo?,
        thrImg: FileNameVo?
    ): CsErrorReportCreateDto {
        return CsErrorReportCreateDto(
            errType = reqBody.errType,
            contentsId = reqBody.contentsId,
            reportMemberId = memberId,
            reportContents = reqBody.reportContents,
            clientOs = reqBody.clientOs,
            clientBrowser = reqBody.clientBrowser,
            firstImgPath = firImg?.path,
            firstImgName = firImg?.name,
            secondImgPath = secImg?.path,
            secondImgName = secImg?.name,
            thirdImgPath = thrImg?.path,
            thirdImgName = thrImg?.name,
            reportStts = ReportSttsType.Submit
        )
    }
}