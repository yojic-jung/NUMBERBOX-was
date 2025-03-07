package com.kamcci.numberbox.restapi.controller.docs

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsAdditionalReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperReadCase
import com.kamcci.numberbox.app.usecase.docs.MathDocsReadCase
import com.kamcci.numberbox.restapi.dto.response.common.PageResponseImpl.Companion.paginate
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * 학습지 조회
 */
@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/math/docs")
class MathDocsReadController(
    private val mathDocsReadCase: MathDocsReadCase,
    private val mathDocsPaperReadCase: MathDocsPaperReadCase,
) {
    companion object {
        const val NOT_MY_DOCS = "자신의 학습지가 아니거나 존재하지 않는 학습지 입니다."
    }

    // 자체제작 수학문제 제작
    @GetMapping("/in-house")
    fun makeInHouseDocs(
        @ModelAttribute @Valid
        request: MathDocsReadDto
    ): ResponseEntity<ResponseData<Any>> {
        val docs = mathDocsReadCase.makeDocs(request)
        return ResponseUtil.ok(mapOf("docs" to docs))
    }

    // 입시 수학문제 제작
    @GetMapping("/ipsi")
    fun readIpsiDocs(
        @ModelAttribute @Valid
        request: MathIpsiDocsReadDto
    ): ResponseEntity<ResponseData<Any>> {
        val docs = mathDocsReadCase.readIpsiDocs(request)
        return ResponseUtil.ok(mapOf("docs" to docs))
    }

    // 유사문제 제작
    @GetMapping("/additional")
    fun readSimilarContents(
        @ModelAttribute @Valid
        readDto: MathDocsAdditionalReadDto
    ): ResponseEntity<ResponseData<Any>> {
        val additionalContents = mathDocsReadCase.readAdditionalContents(readDto)
        return ResponseUtil.ok(mapOf("docs" to additionalContents))
    }

    // 나의 학습지 조회
    @GetMapping("/{docsId}")
    fun myDocs(
        @UserId
        memberId: UUID,
        @PathVariable
        docsId: Long,
    ): ResponseEntity<ResponseData<Any>> {
        val docsPaperVo = mathDocsPaperReadCase.readByIdAndMemberId(docsId, memberId)
            ?: throw BusinessInValidException(NOT_MY_DOCS)
        val docs = mathDocsReadCase.readDocsByDocsPaperId(docsPaperVo.contentsIdList)
        return ResponseUtil.ok(
            mapOf(
                "docsPaper" to docsPaperVo,
                "docs" to docs
            )
        )
    }

    // 나의 학습지 목록 조회
    @GetMapping("/my")
    fun myDocsList(
        @UserId
        memberId: UUID,
        @ModelAttribute
        pageReq: PageRequestImpl
    ): ResponseEntity<ResponseData<Any>> {
        val contents = mathDocsPaperReadCase.readByMemberId(memberId, pageReq)
        val rs = paginate(contents, pageReq) { mathDocsPaperReadCase.countByMemberId(memberId) }
        return ResponseUtil.ok(rs)
    }

}