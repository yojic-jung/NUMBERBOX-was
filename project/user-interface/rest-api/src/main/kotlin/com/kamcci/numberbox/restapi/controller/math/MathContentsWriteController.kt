package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.usecase.math.MathContentsGrammarWriteCase
import com.kamcci.numberbox.app.usecase.math.MathContentsWriteCase
import com.kamcci.numberbox.restapi.dto.request.math.*
import com.kamcci.numberbox.restapi.mapper.math.MathContentsMapper
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * 수학 문제 등록 및 수정
 */
@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/math/content")
class MathContentsWriteController(
    // 문제 제작 목적
    private val mathContentsWriteCase: MathContentsWriteCase,
    private val mathConGrammarWriteCase: MathContentsGrammarWriteCase,
    private val mathContentsMapper: MathContentsMapper,
) {

    // 사용자 제작 문제 등록
    @PostMapping("/user-custom")
    fun createUserCustomContents(
        @UserId memberId: UUID,
        @RequestBody
        @Valid createReq: MathConLicenseCreateRequest
    ): ResponseEntity<ResponseData<Any>> {
        // request to dto 변환
        val contents = mathContentsMapper.toContents(memberId, createReq.contents)

        // 수학문제 생성
        val contentsId = mathContentsWriteCase.createUserCustomContents(contents, createReq.license)
        return ResponseUtil.ok(mapOf("contentsId" to contentsId))
    }

    // 사용자 제작 문제 수정
    @PutMapping("/user-custom")
    fun updateUserCustomContents(
        @UserId memberId: UUID,
        @RequestBody
        @Valid createReq: MathConLicenseUpdtRequest
    ): ResponseEntity<ResponseData<String>> {
        // request to dto 변환
        val contents = mathContentsMapper.toContents(memberId, createReq.contents)

        // 수학문제 생성
        mathContentsWriteCase.updateUserCustomContents(createReq.contentsId, contents, createReq.license)
        return ResponseUtil.ok()
    }

    // 변형문제 등록
    @PostMapping("/trans")
    fun createTransContents(
        @UserId memberId: UUID,
        @RequestBody
        @Valid createReq: MathConTransCreateRequest
    ): ResponseEntity<ResponseData<Any>> {
        // request to dto 변환
        val contents = mathContentsMapper.toContents(memberId, createReq.contents)

        // 수학문제 생성
        val contentsId = mathContentsWriteCase.createTransContents(createReq.orgContentsId, contents)
        return ResponseUtil.ok(mapOf("contentsId" to contentsId))
    }

    // 변형문제 수정
    @PutMapping("/trans")
    fun updateTransContents(
        @UserId memberId: UUID,
        @RequestBody
        @Valid createReq: MathConTransUpdtRequest
    ): ResponseEntity<ResponseData<String>> {
        // request to dto 변환
        val contents = mathContentsMapper.toContents(memberId, createReq.contents)

        // 수학문제 생성
        mathContentsWriteCase.updateTransContents(createReq.contentsId, contents)
        return ResponseUtil.ok()
    }

    // 문제 문법 등록
    @PostMapping("/grammar")
    fun createMathGrammar(
        @UserId memberId: UUID,
        @RequestBody
        @Valid modifyReq: MathContestGrammarModifyRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 문법 정보 저장
        return ResponseUtil.ok(
            mapOf(
                "isUpdate" to mathConGrammarWriteCase.update(
                    modifyReq.contentsId,
                    modifyReq.grammar
                )
            )
        )
    }

    // 문제 삭제
    @DeleteMapping("/{contentsId}")
    fun deleteContents(
        @UserId memberId: UUID,
        @PathVariable contentsId: Long
    ): ResponseEntity<ResponseData<Any>> {
        // 문제 삭제
        mathContentsWriteCase.delete(contentsId, memberId)
        return ResponseUtil.ok(mapOf("isDeleted" to true))
    }

}