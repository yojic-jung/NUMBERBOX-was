package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.usecase.math.MathContentsWriteUseCase
import com.kamcci.numberbox.restapi.dto.request.math.MathConIpsiSrcCreateRequest
import com.kamcci.numberbox.restapi.dto.request.math.MathConSimilarSrcCreateRequest
import com.kamcci.numberbox.restapi.mapper.math.MathContentsMapper
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * 관리자 수학문제
 */
@PreAuthorize("hasRole('MANAGER')")
@RestController
@RequestMapping("/manger/math/content")
class ManagerMathContentsController(
    private val mathContentsWriteUseCase: MathContentsWriteUseCase,
    private val mathContentsMapper: MathContentsMapper
) {
    // 자체제작 문제 등록
    @PostMapping("/in-house")
    fun createInHouseContents(
        @UserId memberId: UUID,
        @RequestBody
        @Valid createReq: MathConSimilarSrcCreateRequest
    ): ResponseEntity<ResponseData<Any>> {
        // request to dto 변환
        val contents = mathContentsMapper.toContents(memberId, createReq.contents)

        // 수학문제 생성
        val contentsId = mathContentsWriteUseCase.createInHouseContents(contents, createReq.similarSrc)

        return ResponseUtil.ok(mapOf("contentsId" to contentsId))
    }

    // 입시 수학문제 등록
    @PostMapping("/ipsi")
    fun createManagerContents(
        @UserId memberId: UUID,
        @RequestBody
        @Valid createReq: MathConIpsiSrcCreateRequest
    ): ResponseEntity<ResponseData<Any>> {
        // request to dto 변환
        val contents = mathContentsMapper.toContents(memberId, createReq.contents)

        // 수학문제 생성
        val contentsId = mathContentsWriteUseCase.createIpsiContents(contents, createReq.ipsiSrc)

        return ResponseUtil.ok(mapOf("contentsId" to contentsId))
    }

}