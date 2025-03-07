package com.kamcci.numberbox.restapi.controller.docs

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperUpdtDto
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperWriteCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.constraints.Positive
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * 학습지 제작 및 수정
 */
@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/math/docs")
class MathDocsWriteController(
    private val mathDocsPaperWriteCase: MathDocsPaperWriteCase
) {
    @PostMapping
    fun create(
        @UserId
        memberId: UUID,
        @RequestBody reqBody: MathDocsPaperCreateDto
    ): ResponseEntity<ResponseData<Any>> {
        return ResponseUtil.ok(mapOf("docsId" to mathDocsPaperWriteCase.create(memberId, reqBody)))
    }

    @PutMapping
    fun update(
        @UserId
        memberId: UUID,
        @RequestBody reqBody: MathDocsPaperUpdtDto
    ): ResponseEntity<ResponseData<String>> {
        mathDocsPaperWriteCase.update(memberId, reqBody)
        return ResponseUtil.ok()
    }


    @DeleteMapping("/{docsId}")
    fun deleteDocs(
        @UserId
        memberId: UUID,
        @Positive
        @PathVariable
        docsId: Long
    ): ResponseEntity<ResponseData<String>> {
        mathDocsPaperWriteCase.delete(docsId, memberId)
        return ResponseUtil.ok()
    }
}