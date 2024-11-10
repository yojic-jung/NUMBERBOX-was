package com.kamcci.numberbox.restapi.controller.docs

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsAdditionalReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperReadUseCase
import com.kamcci.numberbox.app.usecase.docs.MathDocsReadUseCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/math/docs")
class MathDocsReadController(
    private val mathDocsReadUseCase: MathDocsReadUseCase,
    private val mathDocsPaperReadUseCase: MathDocsPaperReadUseCase
) {
    @GetMapping("/in-house")
    fun makeInHouseDocs(
        @ModelAttribute @Valid
        request: MathDocsReadDto
    ): ResponseEntity<ResponseData<Any>> {
        val docs = mathDocsReadUseCase.makeDocs(request)
        return ResponseUtil.ok(mapOf("docs" to docs))
    }

    @GetMapping("/ipsi")
    fun makeIpsiDocs(
        @ModelAttribute @Valid
        request: MathIpsiDocsReadDto
    ): ResponseEntity<ResponseData<Any>> {
        val docs = mathDocsReadUseCase.makeIpsiDocs(request)
        return ResponseUtil.ok(mapOf("docs" to docs))
    }

    @GetMapping("/additional")
    fun readSimilarContents(
        @ModelAttribute @Valid
        readDto: MathDocsAdditionalReadDto
    ): ResponseEntity<ResponseData<Any>> {
        val additionalContents = mathDocsReadUseCase.readAdditionalContents(readDto)
        return ResponseUtil.ok(mapOf("docs" to additionalContents))
    }

    @GetMapping("/{docsId}")
    fun myDocs(
        @UserId
        memberId: UUID,
        @PathVariable
        docsId: Long,
    ): ResponseEntity<ResponseData<Any>> {
        val docsPaperVo = mathDocsPaperReadUseCase.readByIdAndMemberId(docsId, memberId)
            ?: throw BusinessValidException("자신의 학습지가 아니거나 존재하지 않는 학습지 입니다.")
        val docs = mathDocsReadUseCase.readDocsByDocsPaperId(docsPaperVo.contentsIdList)
        return ResponseUtil.ok(
            mapOf(
                "docsPaper" to docsPaperVo,
                "docs" to docs
            )
        )
    }

}