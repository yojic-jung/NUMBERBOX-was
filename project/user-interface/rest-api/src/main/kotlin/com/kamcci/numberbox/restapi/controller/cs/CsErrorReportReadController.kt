package com.kamcci.numberbox.restapi.controller.cs

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.usecase.cs.CsErrorReportReadCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * 고객센터 - 조회
 */
@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/cs/error")
class CsErrorReportReadController(
    private val csErrorReportReadCase: CsErrorReportReadCase
) {
    // 고객센터 내 문의 내역
    @GetMapping("/my")
    fun create(
        @UserId
        memberId: UUID,
    ): ResponseEntity<ResponseData<Any>> {
        return ResponseUtil.ok(mapOf("csErrorReport" to csErrorReportReadCase.readByMemberId(memberId)))
    }
}