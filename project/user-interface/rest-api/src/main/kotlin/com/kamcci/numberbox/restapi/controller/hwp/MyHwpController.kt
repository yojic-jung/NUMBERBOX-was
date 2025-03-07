package com.kamcci.numberbox.restapi.controller.hwp

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsReadCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * 나의 한글 파일
 */
@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/hwp/contents")
class MyHwpController(
    private val hwpConvertContentsReadCase: HwpConvertContentsReadCase,
) {
    // 나의 한글 파일 목록 조회
    @GetMapping("/my")
    fun readMyHwp(
        @UserId userId: UUID
    ): ResponseEntity<ResponseData<Any>> {
        val myConvertContents = hwpConvertContentsReadCase.readAllByMemberId(userId)
        return ResponseUtil.ok(mapOf("contentsList" to myConvertContents))
    }
}