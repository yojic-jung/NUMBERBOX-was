package com.kamcci.numberbox.restapi.controller.docs

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsAdditionalReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperReadCase
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperWriteCase
import com.kamcci.numberbox.app.usecase.docs.MathDocsReadCase
import com.kamcci.numberbox.restapi.dto.response.common.PageResponseImpl.Companion.paginate
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/math/docs")
class MathDocsReadController(
    private val mathDocsReadCase: MathDocsReadCase,
    private val mathDocsPaperReadCase: MathDocsPaperReadCase,
    private val mathDocsPaperWriteCase: MathDocsPaperWriteCase
) {
    @GetMapping("/in-house")
    fun makeInHouseDocs(
        @ModelAttribute @Valid
        request: MathDocsReadDto
    ): ResponseEntity<ResponseData<Any>> {
        val docs = mathDocsReadCase.makeDocs(request)
        return ResponseUtil.ok(mapOf("docs" to docs))
    }

    @GetMapping("/ipsi")
    fun makeIpsiDocs(
        @ModelAttribute @Valid
        request: MathIpsiDocsReadDto
    ): ResponseEntity<ResponseData<Any>> {
        val docs = mathDocsReadCase.makeIpsiDocs(request)
        return ResponseUtil.ok(mapOf("docs" to docs))
    }

    @GetMapping("/additional")
    fun readSimilarContents(
        @ModelAttribute @Valid
        readDto: MathDocsAdditionalReadDto
    ): ResponseEntity<ResponseData<Any>> {
        val additionalContents = mathDocsReadCase.readAdditionalContents(readDto)
        return ResponseUtil.ok(mapOf("docs" to additionalContents))
    }

    @GetMapping("/{docsId}")
    fun myDocs(
        @UserId
        memberId: UUID,
        @PathVariable
        docsId: Long,
    ): ResponseEntity<ResponseData<Any>> {
        val docsPaperVo = mathDocsPaperReadCase.readByIdAndMemberId(docsId, memberId)
            ?: throw BusinessValidException("자신의 학습지가 아니거나 존재하지 않는 학습지 입니다.")
        val docs = mathDocsReadCase.readDocsByDocsPaperId(docsPaperVo.contentsIdList)
        return ResponseUtil.ok(
            mapOf(
                "docsPaper" to docsPaperVo,
                "docs" to docs
            )
        )
    }

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