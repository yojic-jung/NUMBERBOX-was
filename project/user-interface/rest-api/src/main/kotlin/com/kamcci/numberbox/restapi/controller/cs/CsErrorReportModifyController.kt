package com.kamcci.numberbox.restapi.controller.cs

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.usecase.cs.CsErrorReportModifyUseCase
import com.kamcci.numberbox.restapi.dto.request.cs.CsErrorReportCreateRequest
import com.kamcci.numberbox.restapi.mapper.cs.CsErrorReportMapper
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
    private val csErrorReportModifyUseCase: CsErrorReportModifyUseCase
) {
    // 고객센터 신고하기
    @PostMapping
    fun create(
        @UserId
        memberId: UUID,
        @ModelAttribute reqBody: CsErrorReportCreateRequest
    ): ResponseEntity<ResponseData<Any>> {
        val createDto = CsErrorReportMapper.toCreateDto(memberId, reqBody)
        return ResponseUtil.ok(mapOf("csErrorReportId" to csErrorReportModifyUseCase.createReport(createDto)))
    }
}